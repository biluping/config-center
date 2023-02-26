package org.rabbit.flow.component.column;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.TableName;
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
            Assert.notNull(columnLenAnno, "表 [{}] 列 [{}] 必须指定数据库长度", field.getDeclaringClass().getAnnotation(TableName.class).value(), field.getName());
        }
        if (ObjUtil.isNotNull(columnLenAnno)){
            columnMetadata.setLength(columnLenAnno.value());
        }
    }
}
