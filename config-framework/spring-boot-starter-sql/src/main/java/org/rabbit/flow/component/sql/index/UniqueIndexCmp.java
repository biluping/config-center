package org.rabbit.flow.component.sql.index;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.IndexEnum;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.component.sql.SqlBuildCmp;
import org.rabbit.metadata.ColumnIndexMetadata;
import org.rabbit.metadata.ColumnMetadata;

/**
 * 唯一索引
 */
public class UniqueIndexCmp extends SqlBuildCmp {

    @Override
    protected void build(StringBuilder sb, ColumnMetadata columnMetadata) {
        ColumnIndexMetadata indexMetadata = columnMetadata.getIndexMetadata();
        if (ObjUtil.isNotNull(indexMetadata) && ObjUtil.equals(indexMetadata.getIndexEnum(), IndexEnum.UNIQUE)){
            // `field1`

            // `field1`,`field2`.....
            StringBuilder columnStringBuilder = new StringBuilder();
            if (ObjUtil.isNotEmpty(indexMetadata.getFields())){
                for (String c : indexMetadata.getFields()) {
                    columnStringBuilder.append(",`").append(c).append('`');
                }
                columnStringBuilder.deleteCharAt(0);
            }

            // UNIQUE INDEX `key_name`(`field1`,`field2`) USING BTREE
            sb.append("\t");
            sb.append(StrUtil.format(SqlKeywordEnum.UNIQUE_KEY.getKeyword(), indexMetadata.getIndexName(), columnStringBuilder.toString()));
            sb.append(",\n");
        }
    }
}
