package org.rabbit.flow.component.sql.index;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.IndexEnum;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 唯一索引
 */
public class NormalIndexCmp extends SqlBuildCmp {

    @Override
    protected void parse(StringBuilder sb, ColumnMetadata columnMetadata) {
        if (ObjUtil.isNotNull(columnMetadata.getColumnIndexAnno()) && ObjUtil.equals(columnMetadata.getColumnIndexAnno().type(), IndexEnum.NORMAL)){
            sb.append("\t").append(StrUtil.format(SqlKeywordEnum.NORMAL_KEY.getKeyword(), columnMetadata.getColumnIndexAnno().value(), columnMetadata.getColumnName()));
            sb.append(",\n");
        }
    }
}
