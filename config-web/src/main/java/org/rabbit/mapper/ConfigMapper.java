package org.rabbit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.vo.PageResult;
import org.rabbit.wrapper.LambdaQueryWrapperX;

@Mapper
public interface ConfigMapper extends BaseMapperX<ConfigEntity> {
    default PageResult<ConfigEntity> selectPage(ConfigQueryReq req){
        return selectPage(req, new LambdaQueryWrapperX<ConfigEntity>()
                .eq(ConfigEntity::getEnvId, req.getEnvId())
                .likeIfPresent(ConfigEntity::getConfigKey, req.getConfigKey())
                .likeIfPresent(ConfigEntity::getValue, req.getValue()));
    }

    @Select("select max(version) from config_kv where env_id = #{envId} and config_key = #{configKey} and deleted > 0")
    Integer selectMaxVersion(@Param("envId") Long envId, @Param("configKey") String configKey);
}
