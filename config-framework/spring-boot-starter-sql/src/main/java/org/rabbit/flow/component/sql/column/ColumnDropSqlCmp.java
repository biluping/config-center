package org.rabbit.flow.component.sql.column;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 删除列
 */
public class ColumnDropSqlCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(StrUtil.format(SqlKeywordEnum.DROP_COLUMN.getKeyword(), columnMetadata.getTableName()));
        sb.append(" ");
    }

}
