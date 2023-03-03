package org.rabbit.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.rabbit.req.PageParam;
import org.rabbit.vo.PageResult;

import java.util.List;

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

    default PageResult<T> selectPage(PageParam pageParam, Wrapper<T> queryWrapper){
        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        selectPage(page, queryWrapper);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    default List<T> selectListOrderBy(SFunction<T, ?> field, Object value){
        return selectList(new LambdaQueryWrapper<T>().eq(field, value).orderByDesc(field));
    }
}
