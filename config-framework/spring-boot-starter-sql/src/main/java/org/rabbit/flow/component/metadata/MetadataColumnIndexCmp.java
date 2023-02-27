package org.rabbit.flow.component.metadata;

import org.rabbit.annotations.ColumnIndex;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 索引
 */
public class MetadataColumnIndexCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnIndex columnIndexAnno = field.getAnnotation(ColumnIndex.class);
        columnMetadata.setColumnIndexAnno(columnIndexAnno);
    }
}
