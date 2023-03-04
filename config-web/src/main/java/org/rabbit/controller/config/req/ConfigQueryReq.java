package org.rabbit.controller.config.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.req.PageParam;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigQueryReq extends PageParam {

    @Schema(description = "环境id", required = true, example = "123")
    @NotNull(message = "环境ID不能为空")
    private Long envId;

    @Schema(description = "键", required = true, example = "spring.xxx")
    private String configKey;

    @Schema(description = "值", required = true, example = "xxxx")
    private String value;

}
