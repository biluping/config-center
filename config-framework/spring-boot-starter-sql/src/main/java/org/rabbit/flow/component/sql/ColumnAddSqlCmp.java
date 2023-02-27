package org.rabbit.flow.component.sql;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 添加列
 */
public class ColumnAddSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(StrUtil.format(SqlKeywordEnum.ADD_COLUMN.getKeyword(), columnMetadata.getTableName()));
        sb.append(" ");
    }

}
