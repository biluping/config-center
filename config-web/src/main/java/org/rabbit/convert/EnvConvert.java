package org.rabbit.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rabbit.controller.env.vo.EnvVo;
import org.rabbit.entity.EnvEntity;

import java.util.List;

@Mapper
public interface EnvConvert {

    EnvConvert INSTANCE = Mappers.getMapper(EnvConvert.class);

    List<EnvVo> toVo(List<EnvEntity> entity);

}
