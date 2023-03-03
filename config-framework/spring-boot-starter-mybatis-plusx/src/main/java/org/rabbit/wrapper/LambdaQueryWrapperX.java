package org.rabbit.wrapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {

    public LambdaQueryWrapperX<T> likeIfPresent(SFunction<T, ?> column, Object value){
        if (ObjUtil.isNotNull(value)){
            return (LambdaQueryWrapperX<T>)like(column, value);
        }
        return this;
    }

}
