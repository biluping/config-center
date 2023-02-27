package org.rabbit.flow.component.createtable;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 自增拼接
 */
public class ColumnAutoIncrementSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsPrimaryKey() && columnMetadata.getIsAutoIncrement()){
            sb.append(SqlKeywordEnum.AUTO_INCREMENT.getKeyword()).append(" ");
        }
    }

}
