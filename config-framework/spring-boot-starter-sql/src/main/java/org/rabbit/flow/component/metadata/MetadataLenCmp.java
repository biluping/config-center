package org.rabbit.flow.component.metadata;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import org.rabbit.annotations.ColumnLen;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 数据库长度
 */
public class MetadataLenCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        ColumnLen columnLenAnno = field.getAnnotation(ColumnLen.class);
        if (field.getType().equals(String.class)){
            Assert.notNull(columnLenAnno, "表 [{}] 列 [{}] 必须指定数据库长度", columnMetadata.getTableName(), field.getName());
        }
        if (ObjUtil.isNotNull(columnLenAnno)){
            columnMetadata.setLength(columnLenAnno.value());
        }
    }
}
