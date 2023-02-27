package org.rabbit.parser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库表结构解析
 */
public class DbTableParse {

    private static final String QUERY_CURRENT_DATABASE_SQL = "SELECT DATABASE()";

    private static final String QUERY_TABLE_INFO_SQL =
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
        String sql = StrUtil.format(QUERY_TABLE_INFO_SQL, dbName, tableName);
        List<ColumnMetadata> columnMetadataList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ColumnMetadata.class));

        // 表不存在，直接返回空
        if (CollUtil.isEmpty(columnMetadataList)){
            return null;
        }

        // 封装表数据
        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setTableName(tableName);
        tableMetadata.setColumnMetadataList(columnMetadataList);

        return tableMetadata;
    }

}
