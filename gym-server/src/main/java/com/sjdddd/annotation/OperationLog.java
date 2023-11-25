package com.sjdddd.annotation;

import java.lang.annotation.*;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 01:43
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    String operDesc() default "";  // 操作说明
}


