package org.rabbit.controller.env.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnvCreateReq {

    @Schema(name = "项目id", required = true, example = "12")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(name = "环境名称", required = true, example = "dev")
    @NotNull(message = "环境名称不能为空")
    private String envName;
}
