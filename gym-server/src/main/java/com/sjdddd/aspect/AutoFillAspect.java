package com.sjdddd.aspect;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/8/3 13:22
 **/

import com.sjdddd.annotation.AutoFill;
import com.sjdddd.constant.AutoFillConstant;
import com.sjdddd.context.BaseContext;
import com.sjdddd.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类,用于自动填充创建时间和更新时间
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 指定切入点
     */
    // @annotation: 用于匹配当前执行方法持有指定注解的方法
    // execution: 用于匹配当前执行方法的方法名
    @Pointcut("execution(* com.sjdddd.mapper.*.*(..)) && @annotation(com.sjdddd.annotation.AutoFill)")
    public void autoFillPointCut() {

    }

    /**
     * 前置通知,在目标方法执行之前执行
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段填充...");

        //获取方法名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature(); //获取方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解
        OperationType operationType = autoFill.value();//获得操作类型(插入或更新)

        //获取运行时传入的参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        } //如果没有参数,则直接返回
        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //通过反射赋值
        if (operationType == OperationType.INSERT) {
            //插入操作
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                //Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //赋值
                setCreateTime.invoke(entity, now);
                //setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                //setUpdateUser.invoke(entity, currentId);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else if (operationType == OperationType.UPDATE) {
            //更新操作
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //赋值
                setUpdateTime.invoke(entity, now);
                //setUpdateUser.invoke(entity, currentId);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
