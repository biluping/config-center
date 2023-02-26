package org.rabbit.flow.component.createtable;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.PrimaryKeyColumnMetadata;

/**
 * 自增拼接
 */
public class ColumnAutoIncrementSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata instanceof PrimaryKeyColumnMetadata){
            if (((PrimaryKeyColumnMetadata) columnMetadata).getIsAutoIncrement()) {
                sb.append(SqlKeywordEnum.AUTO_INCREMENT.getKeyword()).append(" ");
            }
        }
    }

}
