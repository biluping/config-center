package org.rabbit.controller.config.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConfigUpdateReq {

    @Schema(description = "配置id", required = true, example = "123")
    @NotNull(message = "配置ID不能为空")
    private Long configId;

    @Schema(description = "值", required = true, example = "xxxx")
    @NotNull(message = "值不能为空")
    private String value;

}
