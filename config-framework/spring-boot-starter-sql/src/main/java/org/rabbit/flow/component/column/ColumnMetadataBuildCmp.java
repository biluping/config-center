package org.rabbit.flow.component.column;

import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

/**
 * 解析普通列属性的组件
 */
public abstract class ColumnMetadataBuildCmp implements BiConsumer<ColumnMetadata, Field> {

    abstract void parse(ColumnMetadata columnMetadata, Field field);

    @Override
    public void accept(ColumnMetadata columnMetadata, Field field) {
        parse(columnMetadata, field);
    }
}
