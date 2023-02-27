package org.rabbit.flow.component.sql.column;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * not null 拼接
 */
public class ColumnNotNullSqlCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsNotNull()){
            sb.append(SqlKeywordEnum.NOT_NULL.getKeyword()).append(" ");
        }
    }

}
