package org.rabbit.parser;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
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
        return doParse(entity, new ArrayList<>());
    }


    /**
     * 真正解析普通列信息的地方，递归往上调用
     */
    private static List<ColumnMetadata> doParse(Class<?> entity, List<ColumnMetadata> list){
        // 校验
        if (ObjectUtil.isNull(entity) || entity.equals(Object.class)){
            return list;
        }
        // 遍历每个字段
        for (Field field : entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(TableId.class)){
                continue;
            }
            // 交由流程引擎解析
            ColumnMetadata columnMetadata = FlowExecutor.execute(TableParser.FLOW_NAME, new ColumnMetadata(), field);
            list.add(columnMetadata);
        }
        // 解析父类
        return doParse(entity.getSuperclass(), list);
    }
}
