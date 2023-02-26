package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.ColumnLen;

@Data
@TableName("config_project")
@EqualsAndHashCode(callSuper = true)
public class ProjectEntity extends BaseEntity {

    @ColumnLen(20)
    private String appName;

    @ColumnLen(20)
    private String ownerName;

}
