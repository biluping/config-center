package org.rabbit.controller.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.enums.RespStatusEnum;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.BasicResultVO;

import static org.mockito.Mockito.*;

class ProjectControllerTest {
    @Mock
    ProjectService projectService;
    @InjectMocks
    ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        when(projectService.createProject(any())).thenReturn(Long.valueOf(1));

        BasicResultVO<Long> result = projectController.createProject(new ProjectCreateReq());
        Assertions.assertEquals(new BasicResultVO<>(RespStatusEnum.SUCCESS, RespStatusEnum.SUCCESS.getMsg(), 1L), result);
    }
}