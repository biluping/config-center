package org.rabbit.controller.env;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rabbit.controller.env.req.EnvCreateReq;
import org.rabbit.controller.env.vo.EnvVo;
import org.rabbit.convert.EnvConvert;
import org.rabbit.service.EnvService;
import org.rabbit.vo.BasicResultVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.rabbit.vo.BasicResultVO.success;

@Tag(name = "环境接口")
@Validated
@RequestMapping("/env")
@RestController
public class EnvController {

    @Resource
    private EnvService envService;

    @Operation(summary = "创建环境")
    @PostMapping("/create")
    public BasicResultVO<Long> createEnv(@Valid @RequestBody EnvCreateReq req){
        return success(envService.createEnv(req.getProjectId(), req.getEnvName()));
    }

    @Operation(summary = "查询环境")
    @GetMapping("/list")
    public BasicResultVO<List<EnvVo>> envList(@NotNull(message = "项目ID不能为null") @RequestParam Long projectId){
        return success(EnvConvert.INSTANCE.toVo(envService.envList(projectId)));
    }

    @Operation(summary = "删除环境")
    @DeleteMapping("/delete")
    public BasicResultVO<Boolean> deleteEnv(@NotNull(message = "环境ID不能为null") @RequestParam Long envId){
        return success(envService.deleteEnv(envId));
    }

}
