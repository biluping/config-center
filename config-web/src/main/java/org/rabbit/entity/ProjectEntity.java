package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.annotations.ColumnLen;
import org.rabbit.annotations.TableComment;

@Data
@TableName("config_project")
@TableComment("项目表")
@EqualsAndHashCode(callSuper = true)
public class ProjectEntity extends BaseEntity {

    @ColumnComment("项目名称")
    @ColumnLen(20)
    private String name;

    @ColumnComment("负责人姓名")
    @ColumnLen(20)
    private String ownerName;

}
