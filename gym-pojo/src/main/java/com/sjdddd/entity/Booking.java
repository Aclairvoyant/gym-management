package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 沈佳栋
 * @Description: 预定信息
 * @DateTime: 2023/10/24 21:09
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {

    //预定id
    private Long BookingId;

    //用户id
    private Long UserId;

    //课程id
    private Long CourseId;

    //预定时间
    private LocalDateTime BookingTime;
}
