package org.rabbit.controller.env.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnvVo {

    @Schema(name = "环境id")
    private Long id;

    @Schema(name = "环境名称")
    private String envName;
}
