package org.rabbit.flow.component.metadata;

import cn.hutool.core.util.ObjUtil;
import org.rabbit.annotations.ColumnIndex;
import org.rabbit.metadata.ColumnIndexMetadata;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 索引
 */
public class MetadataColumnIndexCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnIndex columnIndexAnno = field.getAnnotation(ColumnIndex.class);
        if (ObjUtil.isNotNull(columnIndexAnno)){
            // 在数组第一位增加索引第一字段
            String[] otherFields = columnIndexAnno.otherFields();
            String[] allFields = Arrays.copyOf(otherFields, otherFields.length + 1);
            System.arraycopy(otherFields, 0, allFields, 1, otherFields.length);
            allFields[0] = columnMetadata.getColumnName();

            ColumnIndexMetadata metadata = new ColumnIndexMetadata();
            metadata.setIndexName(columnIndexAnno.value());
            metadata.setIndexEnum(columnIndexAnno.type());
            metadata.setFields(allFields);
            metadata.setLen(columnIndexAnno.len());
            columnMetadata.setIndexMetadata(metadata);
        }
    }
}
