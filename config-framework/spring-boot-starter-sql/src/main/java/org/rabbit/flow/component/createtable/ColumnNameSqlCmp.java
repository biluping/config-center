package org.rabbit.flow.component.createtable;

import org.rabbit.metadata.ColumnMetadata;

/**
 * 列名拼接
 */
public class ColumnNameSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append("\t").append('`').append(columnMetadata.getColumnName()).append('`').append(" ");
    }

}
