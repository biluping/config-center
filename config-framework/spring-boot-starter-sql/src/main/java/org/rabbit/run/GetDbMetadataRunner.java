package org.rabbit.run;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjUtil;
import org.rabbit.conf.SqlProperties;
import org.rabbit.metadata.TableMetadata;
import org.rabbit.parser.DbTableParse;
import org.rabbit.parser.TableParser;
import org.rabbit.sql.SqlUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * springboot 启动时，从数据库拉取表结构信息，与项目实体类比较，如果有差异，则打印 ddl 语句
 */
public class GetDbMetadataRunner implements ApplicationRunner {

    @Resource
    private DbTableParse dbTableParse;

    @Resource
    private SqlProperties sqlProperties;

    @Override
    public void run(ApplicationArguments args) {
        Assert.notBlank(sqlProperties.getBasePackage(), "请配置 sql.basePackage 属性");

        // 扫描实体类包，获取实体类元数据
        Set<Class<?>> entities = ClassUtil.scanPackage(sqlProperties.getBasePackage(), c -> !ClassUtil.isAbstract(c));
        List<TableMetadata> tableMetadataList = entities.stream().map(TableParser::parse).collect(Collectors.toList());

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

            // 表存在，比对字段内容
            System.out.println();
        }

    }
}
