package org.rabbit.flow.component.sql;

import org.rabbit.metadata.ColumnMetadata;

import java.util.function.BiConsumer;

/**
 * 建表sql字段构建组件
 */
public abstract class SqlBuildCmp implements BiConsumer<StringBuilder, ColumnMetadata> {

    protected abstract void parse(StringBuilder sb, ColumnMetadata columnMetadata);

    @Override
    public void accept(StringBuilder sb, ColumnMetadata columnMetadata) {
        parse(sb, columnMetadata);
    }
}
