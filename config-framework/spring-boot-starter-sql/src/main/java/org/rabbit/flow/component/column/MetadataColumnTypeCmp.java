package org.rabbit.flow.component.column;

import org.rabbit.enums.JDBCTypeEnum;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 处理列类型
 */
public class MetadataColumnTypeCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        // 列类型
        columnMetadata.setColumnClass(field.getType());

        // jdbc 类型
        String jdbcType = JDBCTypeEnum.getJdbcType(columnMetadata.getColumnClass(), columnMetadata.getLength());
        columnMetadata.setJdbcType(jdbcType);
    }
}
