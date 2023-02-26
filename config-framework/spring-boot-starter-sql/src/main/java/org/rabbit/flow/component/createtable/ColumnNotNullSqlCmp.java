package org.rabbit.flow.component.createtable;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * not null 拼接
 */
public class ColumnNotNullSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsNotNull()){
            sb.append(SqlKeywordEnum.NOT_NULL.getKeyword()).append(" ");
        }
    }

}
