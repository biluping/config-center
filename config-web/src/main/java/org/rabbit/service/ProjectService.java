package org.rabbit.service;

import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.controller.project.req.ProjectQueryReq;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.vo.PageResult;

public interface ProjectService {

    /**
     * 创建项目
     */
    Long createProject(ProjectCreateReq projectCreateReq);

    /**
     * 查询项目
     */
    PageResult<ProjectEntity> getProjectPage(ProjectQueryReq req);

    /**
     * 删除项目
     */
    Boolean deleteProject(Long projectId);

    /**
     * 根据id获取
     */
    ProjectEntity getById(Long projectId);
}
