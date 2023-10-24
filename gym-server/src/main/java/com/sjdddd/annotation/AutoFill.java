package com.sjdddd.annotation;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/10/24 21:32
 **/

import com.sjdddd.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识需要进行自动填充的字段的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    //指定数据库操作类型
    OperationType value();
}
