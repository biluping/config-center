package org.rabbit.flow.component.createtable;

import cn.hutool.core.util.StrUtil;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 列名拼接
 */
public class ColumnNameSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        String tableName = StrUtil.toUnderlineCase(columnMetadata.getColumnName());
        sb.append("\t").append('`').append(tableName).append('`').append(" ");
    }

}
