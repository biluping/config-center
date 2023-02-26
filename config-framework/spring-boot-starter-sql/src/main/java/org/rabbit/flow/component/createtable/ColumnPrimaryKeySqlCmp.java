package org.rabbit.flow.component.createtable;

import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.PrimaryKeyColumnMetadata;

/**
 * 主键拼接
 */
public class ColumnPrimaryKeySqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (PrimaryKeyColumnMetadata.class.equals(columnMetadata.getClass())){
            sb.append(SqlKeywordEnum.PRIMARY_KEY.getKeyword()).append(" ");
        }
    }

}
