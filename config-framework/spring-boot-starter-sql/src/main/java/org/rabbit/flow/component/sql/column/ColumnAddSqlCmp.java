package org.rabbit.flow.component.sql.column;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 添加列
 */
public class ColumnAddSqlCmp extends SqlBuildCmp {

    @Override
    protected void build(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(StrUtil.format(SqlKeywordEnum.ADD_COLUMN.getKeyword(), columnMetadata.getTableName()));
        sb.append(" ");
    }

}
