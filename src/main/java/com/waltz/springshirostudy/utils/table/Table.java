package com.waltz.springshirostudy.utils.table;

/**
 * @author MrWei
 * @version 1.0.0
 * @date 2023/1/3 14:34
 * @description 表名注解
 */
import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})//次注解作用于类和字段上
public @interface Table {
    String tableName() default ""; //默认表名为空
    String keyFields() default "id"; //默认主键为id
}

