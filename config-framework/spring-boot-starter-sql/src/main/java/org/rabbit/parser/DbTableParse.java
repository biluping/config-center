package org.rabbit.parser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.rabbit.metadata.ColumnIndexMetadata;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据库表结构解析
 */
public class DbTableParse {

    private static final String QUERY_CURRENT_DATABASE_SQL = "SELECT DATABASE()";

    private static final String QUERY_COLUMN_INFO_SQL =
            "SELECT " +
            "    TABLE_NAME AS 'table_name'," +
            "    COLUMN_NAME AS 'column_name'," +
            "    COLUMN_TYPE AS 'jdbc_type'," +
            "    !IS_NULLABLE AS 'is_not_null'," +
            "    COLUMN_DEFAULT AS 'default_value'," +
            "    COLUMN_COMMENT AS 'comment'," +
            "    COLUMN_KEY = 'PRI' AS 'is_primary_key'," +
            "    EXTRA = 'auto_increment' AS 'is_auto_increment'" +
            "FROM " +
            "    INFORMATION_SCHEMA.COLUMNS " +
            "WHERE " +
            "    TABLE_SCHEMA = '{}' and TABLE_NAME = '{}' " +
            "ORDER BY " +
            "    TABLE_NAME, ORDINAL_POSITION";
    
    private static final String QUERY_TABLE_COMMENT_SQL = 
            "SELECT " +
            "   table_comment as table_comment " +
            "FROM " +
            "   information_schema.TABLES " +
            "WHERE " +
            "   table_schema = '{}' and table_name = '{}';";

    private static final String QUERY_TABLE_INDEX_SQL = "show index from {}";

    /**
     * 数据库名称缓存
     */
    public static String dbName;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 解析表结构，封装成对象
     */
    public TableMetadata parse(String tableName) {
        Assert.notBlank(tableName, "表名不能为空");

        // 查询当前数据名称
        if (StrUtil.isBlank(dbName)){
            dbName = jdbcTemplate.queryForObject(QUERY_CURRENT_DATABASE_SQL, String.class);
        }

        // 查询封装表结构
        String sql = StrUtil.format(QUERY_COLUMN_INFO_SQL, dbName, tableName);
        List<ColumnMetadata> columnMetadataList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ColumnMetadata.class));

        // 表不存在，直接返回空
        if (CollUtil.isEmpty(columnMetadataList)){
            return null;
        }

        // 表索引填充
        List<ShowIndexTable> indexTableList = jdbcTemplate.query(StrUtil.format(QUERY_TABLE_INDEX_SQL, tableName), new BeanPropertyRowMapper<>(ShowIndexTable.class));
        Map<String, ColumnIndexMetadata> indexMetadataMap = DbTableIndexParse.parse(indexTableList);
        for (ColumnMetadata columnMetadata : columnMetadataList) {
            columnMetadata.setIndexMetadata(indexMetadataMap.get(columnMetadata.getColumnName()));
        }
        
        // 查询表注释
        String tableComment = jdbcTemplate.queryForObject(StrUtil.format(QUERY_TABLE_COMMENT_SQL, dbName, tableName), String.class);

        // 封装表数据
        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setTableName(tableName);
        tableMetadata.setTableComment(tableComment);
        tableMetadata.setColumnMetadataList(columnMetadataList);

        return tableMetadata;
    }

    @Data
    static class ShowIndexTable {
        private String Table;
        private Boolean Non_unique;
        private String Key_name;
        private Integer Seq_in_index;
        private String Column_name;
    }
}
