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
}
