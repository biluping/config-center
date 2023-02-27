package org.rabbit.flow.component.sql;

import org.rabbit.metadata.ColumnMetadata;

/**
 * 列名拼接
 */
public class ColumnNameSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        // 建表语句中，增加 \t 进行个时候，改表语句因为前面有 alter table ... add column ,所以不需要 \t
        if (sb.length() == 0){
            sb.append("\t");
        }
        sb.append('`').append(columnMetadata.getColumnName()).append('`').append(" ");
    }

}
