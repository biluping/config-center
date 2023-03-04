package org.rabbit.service;

import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.vo.PageResult;

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
}
