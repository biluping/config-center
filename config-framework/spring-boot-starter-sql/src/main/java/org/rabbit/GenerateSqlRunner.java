package org.rabbit;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjUtil;
import org.rabbit.conf.SqlProperties;
import org.rabbit.metadata.ColumnMetadata;
import org.rabbit.metadata.TableMetadata;
import org.rabbit.parser.DbTableParse;
import org.rabbit.parser.TableParser;
import org.rabbit.sql.SqlUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * springboot 启动时，从数据库拉取表结构信息，与项目实体类比较，如果有差异，则打印 ddl 语句
 */
public class GenerateSqlRunner implements ApplicationRunner {

    @Resource
    private DbTableParse dbTableParse;

    @Resource
    private SqlProperties sqlProperties;

    @Override
    public void run(ApplicationArguments args) {
        Assert.notBlank(sqlProperties.getBasePackage(), "请配置 sql.basePackage 属性");

        // 扫描实体类包，获取实体类元数据
        Set<Class<?>> entities = ClassUtil.scanPackage(sqlProperties.getBasePackage(), c -> !ClassUtil.isAbstract(c));
        List<TableMetadata> tableMetadataList = entities.stream().map(TableParser::parse).toList();

        // 查询数据库，获取表结构信息
        Map<String, TableMetadata> dbMetadataMap = tableMetadataList.stream()
                .map(TableMetadata::getTableName)
                .map(tableName -> dbTableParse.parse(tableName))
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toMap(TableMetadata::getTableName, Function.identity()));

        // 对比，不存在表则生成建表语句，存在则对比字段，不同生成修改语句
        for (TableMetadata tableMetadata : tableMetadataList) {
            String tableName = tableMetadata.getTableName();
            TableMetadata dbMetadata = dbMetadataMap.get(tableName);

            // 表不存在，打印建表语句
            if (ObjUtil.isNull(dbMetadata)){
                System.err.println(SqlUtils.createTableSql(tableMetadata));
                continue;
            }

            // 比对表注释，不同则打印修改表注释语句
            if (ObjUtil.notEqual(dbMetadata.getTableComment(), tableMetadata.getTableComment())){
                System.err.println(SqlUtils.alterTableCommentSql(tableMetadata));
            }

            // 比对字段内容、比对索引
            // 1、字段相同、内容不同，修改
            // 2、表字段多余，删除
            // 3、表字段缺少，增加
            Map<String, ColumnMetadata> dbColumnMap = CollUtil.toMap(dbMetadata.getColumnMetadataList(), new HashMap<>(), ColumnMetadata::getColumnName);
            for (ColumnMetadata columnMetadata : tableMetadata.getColumnMetadataList()) {
                ColumnMetadata dbColumnMetadata = dbColumnMap.get(columnMetadata.getColumnName());

                // 表字段缺少，构造 add column sql
                if (ObjUtil.isNull(dbColumnMetadata)){
                    System.err.println(SqlUtils.addColumnSql(columnMetadata));
                }
                // 表字段变化，构造 modify column sql
                else if (ObjUtil.notEqual(columnMetadata, dbColumnMetadata)) {
                    System.err.println(SqlUtils.modifyColumnSql(columnMetadata));
                }
                // 如果代码有索引，db 无索引，则新增索引
                else if (ObjUtil.isNull(dbColumnMetadata.getIndexMetadata()) && ObjUtil.isNotNull(columnMetadata.getIndexMetadata())){
                    System.err.println(SqlUtils.addIndexSql(columnMetadata));
                }
                // 如果代码有索引，db 有索引，且两者不一致，则先删除索引，后增加索引
                else if (ObjUtil.isNotNull(dbColumnMetadata.getIndexMetadata())&&ObjUtil.isNotNull(columnMetadata.getIndexMetadata())&&
                        ObjUtil.notEqual(columnMetadata.getIndexMetadata(), dbColumnMetadata.getIndexMetadata())){
                    System.err.println(SqlUtils.dropIndexSql(columnMetadata));
                    System.err.println(SqlUtils.addIndexSql(columnMetadata));
                }

                // 处理完，列从 map 中去除，最后留下来的是多余的列，需要被删除
                dbColumnMap.remove(columnMetadata.getColumnName());
            }

            // 多余的字段，构造 drop column sql
            for (Map.Entry<String, ColumnMetadata> entry : dbColumnMap.entrySet()) {
                ColumnMetadata columnMetadata = entry.getValue();
                System.err.println(SqlUtils.dropColumnSql(columnMetadata));
            }
        }

    }
}
