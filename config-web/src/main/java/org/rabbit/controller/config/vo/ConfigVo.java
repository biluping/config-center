package org.rabbit.controller.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfigVo {

    @Schema(description = "配置id")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "环境id")
    private Long envId;

    @Schema(description = "键")
    private String configKey;

    @Schema(description = "值")
    private String value;

    @Schema(description = "配置版本")
    private Integer version;

    @Schema(description = "是否应用")
    private Integer isApply;

}
