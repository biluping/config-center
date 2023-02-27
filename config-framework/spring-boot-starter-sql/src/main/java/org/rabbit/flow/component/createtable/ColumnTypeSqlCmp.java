package org.rabbit.flow.component.createtable;

import org.rabbit.metadata.ColumnMetadata;

/**
 * 列类型拼接
 */
public class ColumnTypeSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(columnMetadata.getJdbcType()).append(" ");
    }

}
