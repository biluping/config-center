package org.rabbit.flow.component.createtable;

import org.rabbit.enums.JDBCTypeEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 列类型拼接
 */
public class ColumnTypeSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        String jdbcType = JDBCTypeEnum.getJdbcType(columnMetadata.getColumnClass(), columnMetadata.getLength());
        sb.append(jdbcType).append(" ");
    }

}
