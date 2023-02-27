package org.rabbit.metadata;

import lombok.Data;

/**
 * 列信息
 */
@Data
public class ColumnMetadata {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * java 列类型
     */
    private Class<?> columnClass;

    /**
     * jdbc 列类型
     */
    private String jdbcType;

    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;

    /**
     * 是否自增
     */
    private Boolean isAutoIncrement;

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
