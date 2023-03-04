package org.rabbit.annotations;

import org.rabbit.enums.IndexEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 普通索引
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnIndex {

    /**
     * 索引名称
     */
    String value();

    /**
     * 索引类型
     */
    IndexEnum type() default IndexEnum.NORMAL;

    /**
     * 索引长度
     */
    int len() default 0;

    /**
     * 联合索引字段(包括本字段)
     */
    String[] otherFields() default {};
}
