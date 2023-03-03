package org.rabbit.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.controller.project.vo.ProjectPageVo;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.vo.PageResult;

@Mapper
public interface ProjectConvert {

    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    @Mapping(source = "projectName", target = "name")
    ProjectEntity toEntity(ProjectCreateReq req);

    @Mapping(source = "name", target = "projectName")
    ProjectPageVo toVo(ProjectEntity entity);

    PageResult<ProjectPageVo> toPage(PageResult<ProjectEntity> pageResult);
}
