package org.rabbit.flow.component.metadata;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 是否主键、是否自增
 */
public class MetadataPrimaryKeyCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        TableId tableIdAnno = field.getAnnotation(TableId.class);
        columnMetadata.setIsPrimaryKey(false);
        columnMetadata.setIsAutoIncrement(false);
        if (ObjUtil.isNotNull(tableIdAnno)){
            columnMetadata.setIsPrimaryKey(true);
            columnMetadata.setIsAutoIncrement(ObjUtil.equals(IdType.AUTO, tableIdAnno.type()));
        }
    }
}
