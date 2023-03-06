package org.rabbit.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.vo.ConfigVo;
import org.rabbit.entity.ConfigEntity;
import org.rabbit.vo.PageResult;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    ConfigEntity toEntity(ConfigCreateReq req);

    ConfigCreateReq toCreateReq(ConfigEntity entity);

    PageResult<ConfigVo> toPage(PageResult<ConfigEntity> pageResult);

    List<ConfigVo> toVo(List<ConfigEntity> list);
}
