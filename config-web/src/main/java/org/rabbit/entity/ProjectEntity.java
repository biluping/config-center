package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.annotations.ColumnIndex;
import org.rabbit.annotations.ColumnLen;

@Data
@TableName("config_project")
@EqualsAndHashCode(callSuper = true)
public class ProjectEntity extends BaseEntity {

    @ColumnComment("应用名称")
    @ColumnLen(20)
    private String appName;

    @ColumnIndex("own_index")
    @ColumnComment("负责人姓名")
    @ColumnLen(20)
    private String ownerName;

}
