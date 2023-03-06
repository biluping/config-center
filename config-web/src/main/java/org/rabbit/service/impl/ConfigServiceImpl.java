package org.rabbit.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryHistoryReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.controller.config.req.ConfigUpdateReq;
import org.rabbit.convert.ConfigConvert;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.entity.EnvEntity;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.mapper.ConfigMapper;
import org.rabbit.service.ConfigService;
import org.rabbit.service.EnvService;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.PageResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Resource
    private EnvService envService;

    @Resource
    private ProjectService projectService;


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
            return configMapper.insert(configEntity) == 1;
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
}
