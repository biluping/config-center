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
     * 索引类型
     */
    IndexEnum value();

}
