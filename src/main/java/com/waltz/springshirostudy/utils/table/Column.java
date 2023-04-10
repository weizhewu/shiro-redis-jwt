package com.waltz.springshirostudy.utils.table;

/**
 * @author MrWei
 * @version 1.0.0
 * @date 2023/1/3 14:34
 * @description 字段名注解
 */
import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Column {
    String name() default ""; //表示字段名
    String rule() default ""; //表示额外的规则，例如自增
}

