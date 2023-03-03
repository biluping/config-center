package org.rabbit.controller.project.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.req.PageParam;

/**
 * 项目查询
 */
@Data
@Schema(description = "项目查询请求")
@EqualsAndHashCode(callSuper = true)
public class ProjectQueryReq extends PageParam {

    @Schema(description = "项目名称", required = true, example = "网关服务")
    private String projectName;

    @Schema(description = "负责人名称", required = true, example = "常征")
    private String ownerName;
}
