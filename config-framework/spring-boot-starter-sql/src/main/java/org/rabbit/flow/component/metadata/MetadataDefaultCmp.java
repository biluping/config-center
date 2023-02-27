package org.rabbit.flow.component.metadata;

import cn.hutool.core.util.ObjectUtil;
import org.rabbit.annotations.ColumnDefault;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 默认值填充
 */
public class MetadataDefaultCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnDefault columnDefaultAnno = field.getAnnotation(ColumnDefault.class);
        if (ObjectUtil.isNotNull(columnDefaultAnno)){
            columnMetadata.setDefaultValue(columnDefaultAnno.value());
        }
    }
}
