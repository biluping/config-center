package org.rabbit.service;

import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryHistoryReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.controller.config.req.ConfigUpdateReq;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.vo.PageResult;

import java.util.List;

public interface ConfigService {

    /**
     * 新建配置
     */
    Boolean createConfig(ConfigCreateReq req);

    /**
     * 查询配置
     */
    PageResult<ConfigEntity> getConfigPage(ConfigQueryReq req);

    /**
     * 删除配置
     */
    Boolean deleteConfig(Long configId);

    /**
     * 查询 key 的历史发布版本
     */
    List<ConfigEntity> getHistoryConfig(ConfigQueryHistoryReq req);

    /**
     * 更新配置
     */
    Boolean updateConfig(ConfigUpdateReq req);
}
