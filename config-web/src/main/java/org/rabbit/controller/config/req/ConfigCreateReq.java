package org.rabbit.controller.config.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConfigCreateReq {

    @Schema(description = "环境id", required = true, example = "123")
    @NotNull(message = "环境ID不能为空")
    private Long envId;

    @Schema(description = "键", required = true, example = "spring.xxx")
    @NotNull(message = "键不能为空")
    private String configKey;

    @Schema(description = "值", required = true, example = "xxxx")
    @NotNull(message = "值不能为空")
    private String value;

}
