package org.rabbit.service;

import org.rabbit.entity.EnvEntity;

import java.util.List;

public interface EnvService {

    /**
     * 保存环境
     */
    Long createEnv(Long projectId, String envName);

    /**
     * 查询环境
     */
    List<EnvEntity> envList(Long projectId);

    /**
     * 删除整个项目环境
     */
    boolean deleteProjectEnv(Long projectId);

    /**
     * 删除单个环境
     */
    boolean deleteEnv(Long envId);

    /**
     * 根据ID取
     */
    EnvEntity getById(Long envId);
}
