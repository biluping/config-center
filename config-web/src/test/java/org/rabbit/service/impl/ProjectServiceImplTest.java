package org.rabbit.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.mapper.ProjectMapper;
import org.rabbit.service.EnvService;

import static org.mockito.Mockito.*;

class ProjectServiceImplTest {
    @Mock
    ProjectMapper projectMapper;
    @Mock
    EnvService envService;
    @InjectMocks
    ProjectServiceImpl projectServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        when(projectMapper.exists(any(), any())).thenReturn(true);
        when(envService.createEnv(anyLong(), anyString())).thenReturn(Long.valueOf(1));

        ProjectCreateReq req = new ProjectCreateReq();
        req.setProjectName("moon");
        req.setOwnerName("owner");
        req.setEnvName("dev");

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                ()->projectServiceImpl.createProject(req));
    }
}
