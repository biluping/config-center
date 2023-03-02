package org.rabbit.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.entity.ProjectEntity;

@Mapper
public interface ProjectConvert {

    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    @Mapping(source = "projectName", target = "name")
    ProjectEntity toProjectEntity(ProjectCreateReq req);
}
