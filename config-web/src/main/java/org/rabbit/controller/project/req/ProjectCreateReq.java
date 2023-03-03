package org.rabbit.controller.project.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "项目创建请求")
public class ProjectCreateReq {

    @Schema(description = "项目名称", required = true, example = "网关服务")
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @Schema(description = "负责人名称", required = true, example = "常征")
    @NotBlank(message = "负责人名称不能为空")
    private String ownerName;

    @Schema(description = "环境名称", required = true, example = "dev")
    @NotBlank(message = "环境名称不能为空")
    private String envName;
}
