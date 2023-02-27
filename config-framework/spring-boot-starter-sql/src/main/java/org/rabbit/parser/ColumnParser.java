package org.rabbit.parser;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.metadata.ColumnMetadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 普通列解析，不解析主键列
 */
public class ColumnParser {

    /**
     * 解析普通列信息
     */
    public static List<ColumnMetadata> parse(Class<?> entity){
        TableName tableNameAnno = entity.getAnnotation(TableName.class);
        return doParse(entity, tableNameAnno.value(), new ArrayList<>());
    }


    /**
     * 真正解析普通列信息的地方，递归往上调用
     */
    private static List<ColumnMetadata> doParse(Class<?> entity, String tableName, List<ColumnMetadata> list){
        // 校验
        if (ObjectUtil.isNull(entity) || entity.equals(Object.class)){
            return list;
        }
        // 遍历每个字段
        for (Field field : entity.getDeclaredFields()) {

            // 考虑到无法从 entity 的父类的 Field 对象上拿到 @TableName 注解，所以这里直接设置了 tableName
            ColumnMetadata columnMetadata = new ColumnMetadata();
            columnMetadata.setTableName(tableName);
            // 交由流程引擎解析
            FlowExecutor.execute(TableParser.FLOW_NAME, columnMetadata, field);
            // 如果是主键，放在最前
            if (columnMetadata.getIsPrimaryKey()){
                list.add(0, columnMetadata);
            } else {
                list.add(columnMetadata);
            }

        }
        // 解析父类
        return doParse(entity.getSuperclass(), tableName, list);
    }
}
