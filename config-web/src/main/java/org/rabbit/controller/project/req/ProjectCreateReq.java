package org.rabbit.controller.project.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectCreateReq {

    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @NotBlank(message = "负责人名称不能为空")
    private String ownerName;

    @NotBlank(message = "环境名称不能为空")
    private String envName;
}
