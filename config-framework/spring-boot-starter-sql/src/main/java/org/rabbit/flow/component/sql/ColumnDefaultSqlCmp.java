package org.rabbit.flow.component.sql;

import cn.hutool.core.util.ObjUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * default 拼接
 */
public class ColumnDefaultSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (ObjUtil.isNotNull(columnMetadata.getDefaultValue())) {
            sb.append(SqlKeywordEnum.DEFAULT.getKeyword())
                    .append(" ")
                    .append(columnMetadata.getDefaultValue())
                    .append(" ");
        }
    }

}
