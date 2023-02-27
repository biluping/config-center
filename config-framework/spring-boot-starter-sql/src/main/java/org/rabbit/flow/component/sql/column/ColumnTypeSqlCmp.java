package org.rabbit.flow.component.sql.column;

import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 列类型拼接
 */
public class ColumnTypeSqlCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(columnMetadata.getJdbcType()).append(" ");
    }

}
