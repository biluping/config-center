package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.annotations.ColumnLen;
import org.rabbit.annotations.ColumnTinyint;
import org.rabbit.annotations.TableComment;

@Data
@TableName("config_kv")
@TableComment("键值对配置表")
@EqualsAndHashCode(callSuper = true)
public class ConfigEntity extends BaseEntity {

    @ColumnComment("项目id")
    private Long projectId;

    @ColumnComment("环境id")
    private Long envId;

    @ColumnComment("键")
    @ColumnLen(50)
    private String key;

    @ColumnComment("值")
    @ColumnLen(2000)
    private String value;

    @ColumnComment("配置版本")
    private Integer version;

    @ColumnTinyint
    @ColumnComment("是否应用")
    private Integer isApply;

}
