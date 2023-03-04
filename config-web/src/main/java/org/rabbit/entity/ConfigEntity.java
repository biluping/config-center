package org.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rabbit.annotations.*;
import org.rabbit.enums.IndexEnum;

@Data
@TableName("config_kv")
@TableComment("键值对配置表")
@EqualsAndHashCode(callSuper = true)
public class ConfigEntity extends BaseEntity {

    @ColumnComment("项目id")
    private Long projectId;

    @ColumnComment("环境id")
    private Long envId;

    @ColumnIndex(value = "config_key_unique_key", type = IndexEnum.UNIQUE, otherFields = {"deleted"})
    @ColumnComment("键")
    @ColumnLen(50)
    private String configKey;

    @ColumnComment("值")
    @ColumnLen(2000)
    private String value;

    @ColumnComment("配置版本")
    @ColumnDefault("0")
    private Integer version;

    @ColumnTinyint
    @ColumnDefault("0")
    @ColumnComment("是否应用")
    private Integer isApply;

}
