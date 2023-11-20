package com.sjdddd.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:32
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
    * 日志id
    */
    //@ApiModelProperty(value="日志id")
    private Long logId;

    /**
    * 用户id
    */
    //@ApiModelProperty(value="用户id")
    private Long userId;

    /**
    * 操作类型
    */
    //@ApiModelProperty(value="操作类型")
    private String actionType;

    /**
    * 操作日期时间
    */
    //@ApiModelProperty(value="操作日期时间")
    private Date actionDate;
}
