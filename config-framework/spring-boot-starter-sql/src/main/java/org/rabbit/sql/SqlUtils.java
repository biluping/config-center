package org.rabbit.sql;

import cn.hutool.core.util.StrUtil;
import org.rabbit.enums.IndexEnum;
import org.rabbit.enums.SqlKeywordEnum;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.flow.component.sql.column.*;
import org.rabbit.flow.component.sql.index.NormalIndexCmp;
import org.rabbit.flow.component.sql.index.PrimaryKeyIndexCmp;
import org.rabbit.flow.component.sql.index.UniqueIndexCmp;
import org.rabbit.metadata.ColumnIndexMetadata;
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
     * 建表时，构建列索引sql的流程
     */
    public static final String CREATE_COLUMN_INDEX_SQL_BUILD_FLOW = "create-column-index-sql-build-flow";
    /**
     * 改表时，构建添加列定义sql的流程
     */
    public static final String ADD_COLUMN_SQL_BUILD_FLOW = "add-column-sql-build-flow";
    /**
     * 改表时，构建修改列定义sql的流程
     */
    public static final String MODIFY_COLUMN_SQL_BUILD_FLOW = "modify-column-sql-build-flow";
    /**
     * 改表时，构建删除表字段sql的流程
     */
    public static final String DROP_COLUMN_SQL_BUILD_FLOW = "drop-column-sql-build-flow";


    // 初始化列 sql 构建流程
    static {
        // 建表时构建列定义相关流程组件
        List<Class<?>> createColumnCmpList = new ArrayList<>();
        createColumnCmpList.add(ColumnNameSqlCmp.class);
        createColumnCmpList.add(ColumnTypeSqlCmp.class);
        createColumnCmpList.add(ColumnNotNullSqlCmp.class);
        createColumnCmpList.add(ColumnDefaultSqlCmp.class);
        createColumnCmpList.add(ColumnAutoIncrementSqlCmp.class);
        createColumnCmpList.add(ColumnCommentSqlCmp.class);
        FlowExecutor.addFlow(CREATE_COLUMN_SQL_BUILD_FLOW, createColumnCmpList);

        // 建表时，构建列索引相关流程组件
        List<Class<?>> createIndexCmpList = new ArrayList<>();
        createIndexCmpList.add(PrimaryKeyIndexCmp.class);
        createIndexCmpList.add(UniqueIndexCmp.class);
        createIndexCmpList.add(NormalIndexCmp.class);
        FlowExecutor.addFlow(CREATE_COLUMN_INDEX_SQL_BUILD_FLOW, createIndexCmpList);

        // 添加表字段时，相关流程组件
        List<Class<?>> addColumnCmpList = new ArrayList<>(createColumnCmpList);
        addColumnCmpList.add(0, ColumnAddSqlCmp.class);
        FlowExecutor.addFlow(ADD_COLUMN_SQL_BUILD_FLOW, addColumnCmpList);

        // 修改表字段时，相关流程组件
        List<Class<?>> modifyColumnCmpList = new ArrayList<>(createColumnCmpList);
        modifyColumnCmpList.add(0, ColumnModifySqlCmp.class);
        FlowExecutor.addFlow(MODIFY_COLUMN_SQL_BUILD_FLOW, modifyColumnCmpList);

        // 删除表字段时，相关流程组件
        List<Class<?>> dropColumnCmpList = new ArrayList<>();
        dropColumnCmpList.add(ColumnDropSqlCmp.class);
        dropColumnCmpList.add(ColumnNameSqlCmp.class);
        FlowExecutor.addFlow(DROP_COLUMN_SQL_BUILD_FLOW, dropColumnCmpList);
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

        // 列索引 ddl 构建
        for (ColumnMetadata columnMetadata : tableMetadata.getColumnMetadataList()) {
            FlowExecutor.execute(CREATE_COLUMN_INDEX_SQL_BUILD_FLOW, sb, columnMetadata);
        }

        // 删除最后的换行
        sb.delete(sb.length()-2, sb.length());

        return StrUtil.format(SqlKeywordEnum.CREATE_TABLE_TEMPLATE.getKeyword(), tableMetadata.getTableName(), sb.toString(), tableMetadata.getTableComment());
    }

    /**
     * 构造添加列 sql
     */
    public static String addColumnSql(ColumnMetadata columnMetadata){
        StringBuilder sb = FlowExecutor.execute(ADD_COLUMN_SQL_BUILD_FLOW, new StringBuilder(), columnMetadata);
        return sb.append(";").toString();
    }

    /**
     * 构造修改列 sql
     */
    public static String modifyColumnSql(ColumnMetadata columnMetadata){
        StringBuilder sb = FlowExecutor.execute(MODIFY_COLUMN_SQL_BUILD_FLOW, new StringBuilder(), columnMetadata);
        return sb.append(";").toString();
    }

    /**
     * 构造删除列 sql
     */
    public static String dropColumnSql(ColumnMetadata columnMetadata){
        StringBuilder sb = FlowExecutor.execute(DROP_COLUMN_SQL_BUILD_FLOW, new StringBuilder(), columnMetadata);
        return sb.append(";").toString();
    }

    /**
     * 构造修改表注释语句
     */
    public static String alterTableCommentSql(TableMetadata tableMetadata) {
        return StrUtil.format(SqlKeywordEnum.ALTER_TABLE_COMMENT.getKeyword(), tableMetadata.getTableName(), tableMetadata.getTableComment());
    }

    /**
     * 新增索引(普通、唯一)
     */
    public static String addIndexSql(ColumnMetadata columnMetadata) {
        ColumnIndexMetadata indexMetadata = columnMetadata.getIndexMetadata();

        // `field1(10)`,`field2`......
        StringBuilder sb = new StringBuilder();
        for (String c : indexMetadata.getFields()) {
            sb.append(",`").append(c).append("`");
        }

        // 删除最前面的逗号
        sb.deleteCharAt(0);
        String fieldStr = sb.toString();

        // 索引长度拼接
        if (indexMetadata.getLen() > 0){
            fieldStr = fieldStr.replace(columnMetadata.getColumnName(), columnMetadata.getColumnName() + "(" + indexMetadata.getLen() + ")");
        }

        String sqlTemplate = indexMetadata.getIndexEnum()== IndexEnum.NORMAL ? SqlKeywordEnum.ADD_INDEX.getKeyword() : SqlKeywordEnum.ADD_UNIQUE_INDEX.getKeyword();
        return StrUtil.format(sqlTemplate, columnMetadata.getTableName(), indexMetadata.getIndexName(), fieldStr);
    }

    /**
     * 删除索引
     */
    public static String dropIndexSql(ColumnMetadata columnMetadata) {
        return StrUtil.format(SqlKeywordEnum.DROP_INDEX.getKeyword(), columnMetadata.getTableName(), columnMetadata.getIndexMetadata().getIndexName());
    }
}
