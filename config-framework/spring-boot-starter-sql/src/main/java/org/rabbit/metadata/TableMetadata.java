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
     * 列信息
     */
    private List<ColumnMetadata> columnMetadataList;
}
