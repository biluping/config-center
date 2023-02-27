package org.rabbit.sql;

import cn.hutool.core.util.StrUtil;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.createtable.*;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成 sql 语句
 */
public class SqlUtils {

    private static final String COLUMN_SQL_BUILD_FLOW = "create-table-column-flow";
    private static final String CREATE_TABLE_TEMPLATE = "create table {} (\n{}\n);";

    // 初始化列 sql 构建流程
    static {
        List<Class<?>> list = new ArrayList<>();
        list.add(ColumnNameSqlCmp.class);
        list.add(ColumnTypeSqlCmp.class);
        list.add(ColumnNotNullSqlCmp.class);
        list.add(ColumnDefaultSqlCmp.class);
        list.add(ColumnPrimaryKeySqlCmp.class);
        list.add(ColumnAutoIncrementSqlCmp.class);
        list.add(ColumnCommentSqlCmp.class);
        FlowExecutor.addFlow(COLUMN_SQL_BUILD_FLOW, list);
    }

    public static String createTableSql(TableMetadata tableMetadata){

        // 列 ddl 构建
        StringBuilder sb = new StringBuilder();
        for (ColumnMetadata columnMetadata : tableMetadata.getColumnMetadataList()) {
            FlowExecutor.execute(COLUMN_SQL_BUILD_FLOW, sb, columnMetadata);
            sb.append(",\n");
        }
        // 取出最后的换行
        sb.delete(sb.length()-2, sb.length());

        return StrUtil.format(CREATE_TABLE_TEMPLATE, tableMetadata.getTableName(), sb.toString());
    }
}
