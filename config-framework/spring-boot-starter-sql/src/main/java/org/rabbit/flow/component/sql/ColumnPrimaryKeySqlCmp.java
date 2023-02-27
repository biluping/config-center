package org.rabbit.flow.component.sql;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 主键拼接
 */
public class ColumnPrimaryKeySqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsPrimaryKey()){
            sb.append(SqlKeywordEnum.PRIMARY_KEY.getKeyword()).append(" ");
        }
    }

}
