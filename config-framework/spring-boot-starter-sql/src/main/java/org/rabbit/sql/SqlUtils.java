package org.rabbit.sql;

import cn.hutool.core.util.StrUtil;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.createtable.*;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;
import org.rabbit.parser.TableParser;

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

    public static String createTableSql(Class<?> entity){
        TableMetadata tableMetadata = TableParser.parse(entity);

        // 列 ddl 构建
        StringBuilder sb = new StringBuilder();

        // 主键
        FlowExecutor.execute(COLUMN_SQL_BUILD_FLOW, sb, tableMetadata.getPrimaryKeyMetadata());

        // 其他列
        for (ColumnMetadata columnMetadata : tableMetadata.getColumnMetadataList()) {
            sb.append(",\n");
            FlowExecutor.execute(COLUMN_SQL_BUILD_FLOW, sb, columnMetadata);
        }

        return StrUtil.format(CREATE_TABLE_TEMPLATE, tableMetadata.getTableName(), sb.toString());
    }
}
