package org.rabbit.service.impl;

import cn.hutool.core.lang.Assert;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.controller.project.req.ProjectQueryReq;
import org.rabbit.convert.ProjectConvert;
import org.rabbit.entity.ProjectEntity;
import org.rabbit.mapper.ProjectMapper;
import org.rabbit.service.EnvService;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private EnvService envService;

    /**
     * 创建项目
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProject(ProjectCreateReq projectCreateReq) {
        // 唯一校验
        boolean projectNameExist = projectMapper.exists(ProjectEntity::getName, projectCreateReq.getProjectName());
        Assert.isFalse(projectNameExist, "项目已经存在，请勿重复创建");

        // 创建项目
        ProjectEntity projectEntity = ProjectConvert.INSTANCE.toEntity(projectCreateReq);
        projectMapper.insert(projectEntity);

        // 创建默认环境
        envService.save(projectEntity.getId(), projectCreateReq.getEnvName());

        return projectEntity.getId();
    }

    /**
     * 查询项目
     */
    @Override
    public PageResult<ProjectEntity> getProjectPage(ProjectQueryReq req) {
        return projectMapper.selectPage(req);

    }
}
