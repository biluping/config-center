package org.rabbit.controller.project;

import lombok.AllArgsConstructor;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.BasicResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.rabbit.vo.BasicResultVO.success;

@RestController
@AllArgsConstructor
public class ProjectController {

    private ProjectService projectService;

    /**
     * 创建项目
     */
    @PostMapping("create")
    public BasicResultVO<Long> createProject(@Valid @RequestBody ProjectCreateReq projectCreateReq){
        return success(projectService.createProject(projectCreateReq));
    }

}
