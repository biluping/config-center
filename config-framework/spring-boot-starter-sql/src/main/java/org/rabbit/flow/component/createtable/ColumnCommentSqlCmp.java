package org.rabbit.flow.component.createtable;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.metadata.ColumnMetadata;

/**
 * comment 拼接
 */
public class ColumnCommentSqlCmp extends ColumnSqlBuildCmp {

    @Override
    void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (ObjUtil.isNotNull(columnMetadata.getComment())&& StrUtil.isNotBlank(columnMetadata.getComment())){
            sb.append(SqlKeywordEnum.COMMENT.getKeyword()).append(" ").append(columnMetadata.getComment());
        }
    }

}
