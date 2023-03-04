package org.rabbit.flow.component.sql.column;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * comment 拼接
 */
public class ColumnCommentSqlCmp extends SqlBuildCmp {

    @Override
    protected void build(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (ObjUtil.isNotNull(columnMetadata.getComment())&& StrUtil.isNotBlank(columnMetadata.getComment())){
            sb.append(SqlKeywordEnum.COMMENT.getKeyword()).append(" '").append(columnMetadata.getComment()).append("'");
        }
    }

}
