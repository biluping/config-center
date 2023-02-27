package org.rabbit.flow.component.column;

import cn.hutool.core.util.ObjectUtil;
import org.rabbit.annotations.ColumnComment;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 字段注释填充
 */
public class MetadataCommentCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnComment columnCommentAnno = field.getAnnotation(ColumnComment.class);
        if (ObjectUtil.isNotNull(columnCommentAnno)){
            columnMetadata.setComment(columnCommentAnno.value());
        }
    }
}
