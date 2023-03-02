package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.annotations.ColumnLen;
import org.rabbit.annotations.TableComment;

@Data
@TableName("config_env")
@TableComment("环境表")
@EqualsAndHashCode(callSuper = true)
public class EnvEntity extends BaseEntity {

    @ColumnComment("环境名称")
    @ColumnLen(20)
    private String envName;

    @ColumnComment("应用id")
    private Long projectId;
}
