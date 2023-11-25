package com.sjdddd.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sjdddd.annotation.OperationLog;
import com.sjdddd.constant.JwtClaimsConstant;
import com.sjdddd.constant.MessageConstant;
import com.sjdddd.entity.Log;
import com.sjdddd.entity.User;
import com.sjdddd.exception.BaseException;
import com.sjdddd.properties.JwtProperties;
import com.sjdddd.service.OperateLogsService;
import com.sjdddd.service.UserService;
import com.sjdddd.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 01:43
 **/

@Aspect
@Component
public class OperationLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);


    @Autowired
    private OperateLogsService operatelogsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.sjdddd.annotation.OperationLog)")
    public void operLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        Log operlog = new Log();
        try {

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperationLog opLog = method.getAnnotation(OperationLog.class);
            if (opLog != null) {
                String operDesc = opLog.operDesc();
                operlog.setActionType(operDesc); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operlog.setRequestMethod(methodName); // 请求方法

            // 请求的参数
            Object[] arguments = joinPoint.getArgs();
            //判断参数数组是否为空
            Stream<?> stream = ArrayUtils.isEmpty(arguments) ? Stream.empty() :  Arrays.asList(arguments).stream();
            //过滤 joinPoint.getArgs()返回的数组中携带有Request或者Response对象
            List<Object> logArgs = stream
                    .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                    .collect(Collectors.toList());

            // 先将参数所在的list 转换成json 数组
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(logArgs));
            //再转 json 字符串
            String params = jsonArray.toJSONString();

            operlog.setRequestData(params); // 请求参数
            operlog.setResponseData(JSON.toJSONString(keys)); // 返回结果
            User user = getUser();
            operlog.setUserId(user != null ? user.getUserId() : 0); // 请求用户ID
            operlog.setUserName(user != null ? user.getUserName() : "admin"); // 请求用户名称
            operlog.setActionDate(new Date()); // 创建时间
            operlog.setRequestIp(getIp()); // 请求IP
            operlog.setRequestUri(request.getRequestURI()); // 请求URI
            operatelogsService.save(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        Log operlog = new Log();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            Object[] arguments = joinPoint.getArgs();
            //判断参数数组是否为空
            Stream<?> stream = ArrayUtils.isEmpty(arguments) ? Stream.empty() :  Arrays.asList(arguments).stream();
            //过滤 joinPoint.getArgs()返回的数组中携带有Request或者Response对象
            List<Object> logArgs = stream
                    .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                    .collect(Collectors.toList());

            // 先将参数所在的list 转换成json 数组
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(logArgs));
            //再转 json 字符串
            String params = jsonArray.toJSONString();
            operlog.setRequestData(params); // 请求参数
            operlog.setRequestMethod(methodName); // 请求方法名
            operlog.setExceptionName(e.getClass().getName()); // 异常名称
            operlog.setResponseData(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
            User user = getUser();
            operlog.setUserId(user != null ? user.getUserId() : 0); // 请求用户ID
            operlog.setUserName(user != null ? user.getUserName() : "admin"); // 请求用户名称
            operlog.setActionDate(new Date()); // 创建时间
            operlog.setRequestUri(request.getRequestURI()); // 请求URI
            operlog.setRequestIp(getIp()); // 请求IP

            operatelogsService.save(operlog);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }



    /**
     * 获取用户信息,由于我这边 token 是使用 jwt 生成，所以可以从中获取用户id，再根据id获取用户数据
     *
     * @return
     */
    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = null;
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        if(token != null){
            // 获取 token 中的 user id
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

            Object userId = claims.get(JwtClaimsConstant.USER_ID);

            user = userService.findByUserId(userId);
        }
        return user;
    }

    /**
     * 获取ip
     *
     * @return
     */
    public String getIp() {
        //获得本机的ip和名称
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new BaseException(MessageConstant.REQUEST_IP_FAILED);
        }
        String ip = addr.getHostAddress();
//      String hostname = addr.getHostName();
        return ip;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }
}
