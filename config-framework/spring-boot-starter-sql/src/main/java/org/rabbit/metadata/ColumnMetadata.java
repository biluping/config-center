package org.rabbit.metadata;

import lombok.Data;

/**
 * 列信息，排除主键
 */
@Data
public class ColumnMetadata {

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列类型
     */
    private Class<?> columnClass;

    /**
     * 列长度
     */
    private Integer length;

    /**
     * 是否非空
     */
    private Boolean isNotNull;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 注释
     */
    private String comment;
}
