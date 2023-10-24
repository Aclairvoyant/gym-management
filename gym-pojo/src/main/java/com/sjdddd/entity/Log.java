package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 沈佳栋
 * @Description: 日志信息
 * @DateTime: 2023/10/24 21:16
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

    //日志id
    private Long LogId;

    //用户id
    private Long UserId;

    //操作类型
    private String OperationType;

    //操作时间
    private LocalDateTime OperationTime;
}
