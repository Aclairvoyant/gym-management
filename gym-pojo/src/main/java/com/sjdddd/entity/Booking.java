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
    * 预定表
    */
//@ApiModel(description="预定表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    /**
    * 预定id
    */
    //@ApiModelProperty(value="预定id")
    private Long bookingId;

    /**
    * 用户id
    */
    //@ApiModelProperty(value="用户id")
    private Object userId;

    /**
    * 课程id
    */
    //@ApiModelProperty(value="课程id")
    private Long courseId;

    /**
    * 预定日期时间
    */
    //@ApiModelProperty(value="预定日期时间")
    private Date bookingDate;
}
