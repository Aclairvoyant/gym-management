package com.sjdddd.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/26 02:04
**/
/**
    * 日志表
    */
//@ApiModel(description="日志表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    /**
    * 日志ID
    */
    //@ApiModelProperty(value="日志ID")
    private Long logId;

    /**
    * 用户ID
    */
    //@ApiModelProperty(value="用户ID")
    private Long userId;

    /**
    * 操作类型
    */
    //@ApiModelProperty(value="操作类型")
    private String actionType;

    /**
    * 操作时间
    */
    //@ApiModelProperty(value="操作时间")
    private Date actionDate;

    /**
    * 请求方法
    */
    //@ApiModelProperty(value="请求方法")
    private String requestMethod;

    /**
    * 请求参数
    */
    //@ApiModelProperty(value="请求参数")
    private String requestData;

    /**
    * 返回结果
    */
    //@ApiModelProperty(value="返回结果")
    private String responseData;

    /**
    * 异常名称
    */
    //@ApiModelProperty(value="异常名称")
    private String exceptionName;

    /**
    * 异常信息
    */
    //@ApiModelProperty(value="异常信息")
    private String exceptionMessage;

    /**
    * 请求用户名称
    */
    //@ApiModelProperty(value="请求用户名称")
    private String userName;

    /**
    * 请求URI
    */
    //@ApiModelProperty(value="请求URI")
    private String requestUri;

    /**
    * 请求IP
    */
    //@ApiModelProperty(value="请求IP")
    private String requestIp;
}
