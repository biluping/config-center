package org.rabbit.controller.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rabbit.controller.project.req.ProjectCreateReq;
import org.rabbit.controller.project.req.ProjectQueryReq;
import org.rabbit.controller.project.vo.ProjectPageVo;
import org.rabbit.convert.ProjectConvert;
import org.rabbit.service.ProjectService;
import org.rabbit.vo.BasicResultVO;
import org.rabbit.vo.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.rabbit.vo.BasicResultVO.success;

@Tag(name = "项目接口")
@RestController
@Validated
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 创建项目
     */
    @Operation(summary = "创建项目")
    @PostMapping("create")
    public BasicResultVO<Long> createProject(@Valid @RequestBody ProjectCreateReq projectCreateReq){
        return success(projectService.createProject(projectCreateReq));
    }

    @Operation(summary = "查询项目")
    @PostMapping("page")
    public BasicResultVO<PageResult<ProjectPageVo>> getProjectPage(@Valid @RequestBody ProjectQueryReq req){
        return success(ProjectConvert.INSTANCE.toPage(projectService.getProjectPage(req)));
    }

    @Operation(summary = "删除项目")
    @DeleteMapping("delete")
    public BasicResultVO<Boolean> deleteProject(@NotNull(message = "项目ID不能为null") @RequestParam Long projectId){
        return success(projectService.deleteProject(projectId));
    }
}
