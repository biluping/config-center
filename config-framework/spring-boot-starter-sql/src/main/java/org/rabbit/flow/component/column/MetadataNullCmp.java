package org.rabbit.flow.component.column;

import cn.hutool.core.util.ObjectUtil;
import org.rabbit.annotations.ColumnNull;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 判断是否可以为空
 */
public class MetadataNullCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnNull columnNullAnno = field.getAnnotation(ColumnNull.class);
        columnMetadata.setIsNotNull(ObjectUtil.isNull(columnNullAnno));
    }
}
