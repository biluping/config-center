package org.rabbit.service.impl;

import cn.hutool.core.lang.Assert;
import org.rabbit.entity.EnvEntity;
import org.rabbit.mapper.EnvMapper;
import org.rabbit.service.EnvService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EnvServiceImpl implements EnvService {

    @Resource
    private EnvMapper envMapper;

    /**
     * 保存环境
     */
    @Override
    public Long save(Long projectId, String envName) {
        // 参数校验
        boolean exists = envMapper.exists(EnvEntity::getProjectId, projectId, EnvEntity::getEnvName, envName);
        Assert.isFalse(exists, "projectId [{}] 下环境 [{}] 已存在，请勿重复创建", projectId, envName);

        // 保存
        EnvEntity envEntity = new EnvEntity();
        envEntity.setProjectId(projectId);
        envEntity.setEnvName(envName);
        envMapper.insert(envEntity);

        return envEntity.getId();
    }

}
