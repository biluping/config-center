package org.rabbit.metadata;

import lombok.Data;
import org.rabbit.annotations.ColumnIndex;

import java.util.Objects;

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

    /**
     * 索引注解
     */
    private ColumnIndex columnIndexAnno;

    /**
     * 为什么重写？
     * 用于比较数据库列信息与代码中列信息是否相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnMetadata)) return false;
        ColumnMetadata that = (ColumnMetadata) o;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(jdbcType, that.jdbcType) &&
                Objects.equals(isAutoIncrement, that.isAutoIncrement) &&
                Objects.equals(isNotNull, that.isNotNull) &&
                Objects.equals(defaultValue, that.defaultValue) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName, jdbcType, isAutoIncrement, isNotNull, defaultValue, comment);
    }
}
