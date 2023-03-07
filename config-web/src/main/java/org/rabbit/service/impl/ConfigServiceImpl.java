package org.rabbit.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.slf4j.Slf4j;
import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryHistoryReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.controller.config.req.ConfigUpdateReq;
import org.rabbit.controller.config.vo.ConfigVo;
import org.rabbit.convert.ConfigConvert;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.entity.EnvEntity;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.event.ConfigChangeEvent;
import org.rabbit.mapper.ConfigMapper;
import org.rabbit.service.ConfigService;
import org.rabbit.service.EnvService;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.BasicResultVO;
import org.rabbit.vo.PageResult;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService, ApplicationListener<ConfigChangeEvent> {

    @Resource
    private ConfigMapper configMapper;

    @Resource
    private EnvService envService;

    @Resource
    private ProjectService projectService;

    private final Multimap<Long/*envId*/, DeferredResult<BasicResultVO<List<ConfigVo>>>> deferredResultMap = Multimaps.synchronizedMultimap(HashMultimap.create());

    /**
     * 新建配置
     */
    @Override
    public Boolean createConfig(ConfigCreateReq req) {
        EnvEntity envEntity = envService.getById(req.getEnvId());
        Assert.notNull(envEntity, "环境id [{}] 不存在", envEntity.getId());

        ProjectEntity projectEntity = projectService.getById(envEntity.getProjectId());
        Assert.notNull(projectEntity, "项目id [{}] 不存在", projectEntity.getId());

        boolean exists = configMapper.exists(ConfigEntity::getConfigKey, req.getConfigKey());
        Assert.isFalse(exists, "key [{}] 存在，请勿重复创建", req.getConfigKey());

        // 查询最大的版本号
        Integer version = configMapper.selectMaxVersion(req.getEnvId(), req.getConfigKey());

        ConfigEntity configEntity = ConfigConvert.INSTANCE.toEntity(req);
        configEntity.setProjectId(projectEntity.getId());
        if (ObjUtil.isNotNull(version)){
            configEntity.setVersion(version + 1);
        }

        try{
            boolean ok = configMapper.insert(configEntity) == 1;

            // 通知更新
            SpringUtil.publishEvent(new ConfigChangeEvent(envEntity.getId()));

            return ok;
        } catch (DuplicateKeyException e){
            log.warn("并发异常，配置重复创建, 触发唯一索引校验", e);
            throw new IllegalArgumentException(StrUtil.format("key [{}] 存在，请勿重复创建", req.getConfigKey()));
        }
    }

    /**
     * 查询配置
     */
    @Override
    public PageResult<ConfigEntity> getConfigPage(ConfigQueryReq req) {
        return configMapper.selectPage(req);
    }

    /**
     * 删除配置
     */
    @Override
    public Boolean deleteConfig(Long configId) {
        return configMapper.logicDeleted(ConfigEntity::getId, configId);
    }

    /**
     * 查询 key 的历史发布版本
     */
    @Override
    public List<ConfigEntity> getHistoryConfig(ConfigQueryHistoryReq req) {
        return configMapper.getHistoryConfig(req.getEnvId(), req.getConfigKey());
    }

    /**
     * 更新配置
     */
    @Transactional
    @Override
    public Boolean updateConfig(ConfigUpdateReq req) {
        ConfigEntity configEntity = configMapper.selectOne(ConfigEntity::getId, req.getConfigId());
        Assert.notNull(configEntity, "配置不存在, id = {}", req.getConfigId());
        Assert.notEquals(req.getValue(), configEntity.getValue(), "配置值一致，无需更新");

        // 先删除
        deleteConfig(req.getConfigId());

        // 再新增
        ConfigCreateReq configCreateReq = ConfigConvert.INSTANCE.toCreateReq(configEntity);
        configCreateReq.setValue(req.getValue());
        return createConfig(configCreateReq);
    }

    /**
     * 客户端查询配置
     */
    @Override
    public DeferredResult<BasicResultVO<List<ConfigVo>>> client(Long envId) {
        DeferredResult<BasicResultVO<List<ConfigVo>>> deferredResult = new DeferredResult<>(60000L);

        // 如果环境不存在，直接返回
        EnvEntity envEntity = envService.getById(envId);
        if (ObjUtil.isNull(envEntity)) {
            BasicResultVO<List<ConfigVo>> fail = BasicResultVO.fail(StrUtil.format("环境 id [{}] 不存在", envId));
            deferredResult.setResult(fail);
            return deferredResult;
        }

        // 先存，由线程扫描是否有新key，如果有再返回
        deferredResultMap.put(envId, deferredResult);

        // 超时查询所有返回
        deferredResult.onTimeout(()->{
            List<ConfigEntity> list = configMapper.selectAllApply(envId);
            deferredResult.setResult(BasicResultVO.success(ConfigConvert.INSTANCE.toVo(list)));
        });

        // 完成后，移除，防止内存溢出
        deferredResult.onCompletion(()-> deferredResultMap.remove(envId, deferredResult));

        return deferredResult;
    }

    /**
     * 监听到配置有更新，给客户端响应
     */
    @Override
    public synchronized void onApplicationEvent(ConfigChangeEvent event) {
        // 加锁，防止其他线程向 list 中添加
        Long envId = (Long) event.getSource();
        List<ConfigEntity> list = configMapper.selectAllApply(envId);
        BasicResultVO<List<ConfigVo>> success = BasicResultVO.success(ConfigConvert.INSTANCE.toVo(list));

        // 响应，复制一份，防止并发修改异常
        List<DeferredResult<BasicResultVO<List<ConfigVo>>>> defList = new ArrayList<>(deferredResultMap.get(envId));
        for (DeferredResult<BasicResultVO<List<ConfigVo>>> deferredResult : defList) {
            deferredResult.setResult(success);
        }
    }
}
