package org.rabbit.flow.component.column;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.PrimaryKeyColumnMetadata;

import java.lang.reflect.Field;

/**
 * 主键数据
 */
public class MetadataPrimaryKeyCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        if (columnMetadata instanceof PrimaryKeyColumnMetadata){
            TableId tableIdAnno = field.getAnnotation(TableId.class);
            if (ObjUtil.isNotNull(tableIdAnno) && ObjUtil.equals(tableIdAnno.type(), IdType.AUTO)){
                ((PrimaryKeyColumnMetadata) columnMetadata).setIsAutoIncrement(true);
            }
        }
    }
}
