package org.rabbit.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public interface BaseMapperX<T> extends BaseMapper<T> {

    default boolean exists(SFunction<T, ?> field, Object value) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(field, value);
        return exists(wrapper);
    }

    default boolean exists(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(field1, value1);
        wrapper.eq(field2, value2);
        return exists(wrapper);
    }
}
