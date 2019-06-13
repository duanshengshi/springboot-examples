package com.example.cache.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCacheable {

    String key() default ""; //要存储的key,默认是查询条件的第一个参数

    int expireTime() default 30;//默认30分钟

    TimeUnit unit() default TimeUnit.MINUTES;  //默认值是以分钟为单位

}
