package org.rabbit.flow.component.sql.column;

import cn.hutool.core.util.ObjUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * default 拼接
 */
public class ColumnDefaultSqlCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (ObjUtil.isNotNull(columnMetadata.getDefaultValue())) {
            sb.append(SqlKeywordEnum.DEFAULT.getKeyword())
                    .append(" ")
                    .append(columnMetadata.getDefaultValue())
                    .append(" ");
        }
    }

}
