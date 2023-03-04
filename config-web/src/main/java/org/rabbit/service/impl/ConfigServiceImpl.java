package org.rabbit.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryHistoryReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.convert.ConfigConvert;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.entity.EnvEntity;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.mapper.ConfigMapper;
import org.rabbit.service.ConfigService;
import org.rabbit.service.EnvService;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

        return configMapper.insert(configEntity) == 1;
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
}
