package org.rabbit.service;

public interface EnvService {

    /**
     * 保存环境
     */
    Long save(Long projectId, String envName);
}
