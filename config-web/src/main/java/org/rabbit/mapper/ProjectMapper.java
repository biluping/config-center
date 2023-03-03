package org.rabbit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rabbit.controller.project.req.ProjectQueryReq;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.vo.PageResult;
import org.rabbit.wrapper.LambdaQueryWrapperX;

@Mapper
public interface ProjectMapper extends BaseMapperX<ProjectEntity> {

    /**
     * 查询项目
     */
    default PageResult<ProjectEntity> selectPage(ProjectQueryReq req){
        return selectPage(req, new LambdaQueryWrapperX<ProjectEntity>()
                        .likeIfPresent(ProjectEntity::getName, req.getProjectName())
                        .likeIfPresent(ProjectEntity::getOwnerName, req.getOwnerName())
                        .orderByDesc(ProjectEntity::getId));
    }
}
