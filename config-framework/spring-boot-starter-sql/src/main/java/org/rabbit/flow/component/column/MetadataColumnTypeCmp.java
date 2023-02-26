package org.rabbit.flow.component.column;

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
    }
}
