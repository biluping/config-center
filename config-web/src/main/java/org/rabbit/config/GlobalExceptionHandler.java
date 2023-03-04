package org.rabbit.config;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.rabbit.vo.BasicResultVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
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
        String message = sb.toString();
        log.warn(message);
        return BasicResultVO.fail(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public BasicResultVO<String> validIllegalArgumentExceptionHandler(IllegalArgumentException ex){
        String message = ex.getMessage();
        log.warn(message);
        return BasicResultVO.fail(message);
    }
}
