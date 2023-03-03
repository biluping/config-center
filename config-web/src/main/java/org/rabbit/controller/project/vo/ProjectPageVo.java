package org.rabbit.controller.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProjectPageVo {

    @Schema(description = "id", required = true, example = "1")
    private Long id;

    @Schema(description = "项目名称", required = true, example = "网关服务")
    private String projectName;

    @Schema(description = "负责人名称", required = true, example = "常征")
    private String ownerName;
}
