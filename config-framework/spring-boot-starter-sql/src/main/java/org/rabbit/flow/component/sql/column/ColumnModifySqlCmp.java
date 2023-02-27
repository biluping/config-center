package org.rabbit.flow.component.sql.column;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 修改列
 */
public class ColumnModifySqlCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        sb.append(StrUtil.format(SqlKeywordEnum.MODIFY_COLUMN.getKeyword(), columnMetadata.getTableName()));
        sb.append(" ");
    }

}
