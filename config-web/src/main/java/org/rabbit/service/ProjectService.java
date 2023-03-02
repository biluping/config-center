package org.rabbit.service;

import org.rabbit.controller.project.req.ProjectCreateReq;

public interface ProjectService {

    /**
     * 创建项目
     */
    Long createProject(ProjectCreateReq projectCreateReq);
}
