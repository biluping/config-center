package org.rabbit.flow.component.sql.index;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 主键索引
 */
public class PrimaryKeyIndexCmp extends SqlBuildCmp {

    @Override
    protected void build(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (columnMetadata.getIsPrimaryKey()){
            sb.append("\t").append(StrUtil.format(SqlKeywordEnum.PRIMARY_KEY.getKeyword(), columnMetadata.getColumnName()));
            sb.append(",\n");
        }
    }
}
