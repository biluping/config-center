package org.rabbit.metadata;

import lombok.Data;

import java.util.List;

/**
 * 表信息
 */
@Data
public class TableMetadata {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 列信息
     */
    private List<ColumnMetadata> columnMetadataList;
}
