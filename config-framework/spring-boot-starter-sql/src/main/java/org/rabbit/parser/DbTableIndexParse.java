package org.rabbit.parser;

import cn.hutool.core.util.ObjUtil;
import org.rabbit.enums.IndexEnum;
import org.rabbit.metadata.ColumnIndexMetadata;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库索引解析
 */
public class DbTableIndexParse {

    /**
     * 根据数据库查询的索引，构造 ColumnIndexMetadata 对象返回
     */
    public static Map<String /* 第一位索引列名 */, ColumnIndexMetadata> parse(List<DbTableParse.ShowIndexTable> indexTableList){

        // 根据索引名称分组，组内按照索引顺序排序
        Map<String, List<DbTableParse.ShowIndexTable>> indexNameMap = indexTableList.stream()
                .filter(e -> ObjUtil.notEqual(e.getKey_name(), "PRIMARY"))
                .sorted(Comparator.comparingInt(DbTableParse.ShowIndexTable::getSeq_in_index))
                .collect(Collectors.groupingBy(DbTableParse.ShowIndexTable::getKey_name));

        // 封装成 ColumnIndexMetadata，存入 map 中返回
        Map<String, ColumnIndexMetadata> map = new HashMap<>();
        for (Map.Entry<String, List<DbTableParse.ShowIndexTable>> entry : indexNameMap.entrySet()) {
            String indexName = entry.getKey();
            List<String> columnList = entry.getValue().stream().map(DbTableParse.ShowIndexTable::getColumn_name).toList();

            // 取出联合(普通)索引第一个索引字段
            DbTableParse.ShowIndexTable showIndexTable = entry.getValue().get(0);

            ColumnIndexMetadata metadata = new ColumnIndexMetadata();
            metadata.setIndexName(indexName);
            metadata.setFields(columnList.toArray(new String[0]));

            if (showIndexTable.getNon_unique()){
                metadata.setIndexEnum(IndexEnum.NORMAL);
            } else {
                metadata.setIndexEnum(IndexEnum.UNIQUE);
            }

            map.put(showIndexTable.getColumn_name(), metadata);
        }

        return map;
    }
}
