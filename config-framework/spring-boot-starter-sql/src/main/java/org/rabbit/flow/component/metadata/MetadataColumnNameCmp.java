package org.rabbit.flow.component.metadata;

import cn.hutool.core.util.StrUtil;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;

/**
 * 处理列名
 */
public class MetadataColumnNameCmp extends ColumnMetadataBuildCmp {
    @Override
    void parse(ColumnMetadata columnMetadata, Field field) {
        // 列名
        columnMetadata.setColumnName(StrUtil.toUnderlineCase(field.getName()));
    }
}
