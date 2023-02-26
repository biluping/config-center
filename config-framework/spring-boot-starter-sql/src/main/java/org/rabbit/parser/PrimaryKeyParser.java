package org.rabbit.parser;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import org.rabbit.flow.FlowExecutor;
import org.rabbit.metadata.PrimaryKeyColumnMetadata;

import java.lang.reflect.Field;

public class PrimaryKeyParser {

    /**
     * 解析主键字段
     */
    public static PrimaryKeyColumnMetadata parse(Class<?> entity){

        // 先找到主键字段
        Field field = getTableIdField(entity);
        if (ObjectUtil.isNull(field)){
            throw new IllegalArgumentException("实体类未标识主键字段，请使用 @TableId 进行标识: " + entity);
        }

        return FlowExecutor.execute(TableParser.FLOW_NAME, new PrimaryKeyColumnMetadata(), field);
    }

    /**
     * 递归找类上的标注 TableId 的字段
     */
    private static Field getTableIdField(Class<?> entity) {
        if (entity == null || ObjectUtil.equals(entity, Object.class)){
            return null;
        }
        for (Field field : entity.getDeclaredFields()) {
            if (field.isAnnotationPresent(TableId.class)) {
                return field;
            }
        }
        return getTableIdField(entity.getSuperclass());
    }
}
