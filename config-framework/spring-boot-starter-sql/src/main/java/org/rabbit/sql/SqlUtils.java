package org.rabbit.sql;

import cn.hutool.core.util.StrUtil;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.sql.*;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成 sql 语句
 */
public class SqlUtils {

    /**
     * 建表时，构建列定义sql的流程
     */
    public static final String CREATE_COLUMN_SQL_BUILD_FLOW = "create-column-sql-build-flow";
    /**
     * 改表时，构建添加列定义sql的流程
     */
    public static final String ADD_COLUMN_SQL_BUILD_FLOW = "add-column-sql-build-flow";
    /**
     * 改表时，构建修改列定义sql的流程
     */
    public static final String MODIFY_COLUMN_SQL_BUILD_FLOW = "modify-column-sql-build-flow";


    private static final String CREATE_TABLE_TEMPLATE = "create table {} (\n{}\n);";

    // 初始化列 sql 构建流程
    static {
        // 建表时构建列定义
        List<Class<?>> createColumnCmplist = new ArrayList<>();
        createColumnCmplist.add(ColumnNameSqlCmp.class);
        createColumnCmplist.add(ColumnTypeSqlCmp.class);
        createColumnCmplist.add(ColumnNotNullSqlCmp.class);
        createColumnCmplist.add(ColumnDefaultSqlCmp.class);
        createColumnCmplist.add(ColumnPrimaryKeySqlCmp.class);
        createColumnCmplist.add(ColumnAutoIncrementSqlCmp.class);
        createColumnCmplist.add(ColumnCommentSqlCmp.class);
        FlowExecutor.addFlow(CREATE_COLUMN_SQL_BUILD_FLOW, createColumnCmplist);

        // 添加列定义组件
        ArrayList<Class<?>> addColumnCmpList = new ArrayList<>(createColumnCmplist);
        addColumnCmpList.add(0, ColumnAddSqlCmp.class);
        FlowExecutor.addFlow(ADD_COLUMN_SQL_BUILD_FLOW, addColumnCmpList);
    }

    /**
     * 构造建表 sql
     */
    public static String createTableSql(TableMetadata tableMetadata){

        // 列 ddl 构建
        StringBuilder sb = new StringBuilder();
        for (ColumnMetadata columnMetadata : tableMetadata.getColumnMetadataList()) {
            FlowExecutor.execute(CREATE_COLUMN_SQL_BUILD_FLOW, sb, columnMetadata);
            sb.append(",\n");
        }
        // 取出最后的换行
        sb.delete(sb.length()-2, sb.length());

        return StrUtil.format(CREATE_TABLE_TEMPLATE, tableMetadata.getTableName(), sb.toString());
    }

    /**
     * 构造添加列 sql
     */
    public static String addColumnSql(ColumnMetadata columnMetadata){
        StringBuilder sb = FlowExecutor.execute(ADD_COLUMN_SQL_BUILD_FLOW, new StringBuilder(), columnMetadata);
        return sb.append(";").toString();
    }
}
