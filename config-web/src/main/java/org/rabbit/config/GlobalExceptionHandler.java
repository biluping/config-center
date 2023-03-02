package org.rabbit.config;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.rabbit.vo.BasicResultVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResultVO<String> validExceptionHandler(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (ObjectError err : bindingResult.getAllErrors()) {
            Object field = ReflectUtil.getFieldValue(err, "field");
            sb.append(StrUtil.format("字段:[{}]:{}、", field, err.getDefaultMessage()));
        }
        sb.deleteCharAt(sb.length()-1);
        return BasicResultVO.fail(sb.toString());
    }
}
