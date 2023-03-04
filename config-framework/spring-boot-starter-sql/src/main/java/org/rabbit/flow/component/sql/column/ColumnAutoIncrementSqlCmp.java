package org.rabbit.flow.component.sql.column;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 自增拼接
 */
public class ColumnAutoIncrementSqlCmp extends SqlBuildCmp {

    @Override
    protected void build(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsPrimaryKey() && columnMetadata.getIsAutoIncrement()){
            sb.append(SqlKeywordEnum.AUTO_INCREMENT.getKeyword()).append(" ");
        }
    }

}
