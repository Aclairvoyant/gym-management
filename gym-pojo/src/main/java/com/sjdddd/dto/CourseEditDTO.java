package com.sjdddd.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 22:08
 **/
@Data
public class CourseEditDTO implements java.io.Serializable{
    private Long courseId;

    private String courseName;

    private Long coachId;

    private String coachRealName;

    private BigDecimal courseFee;

    /**
     * 课程开始时间
     */
    //@ApiModelProperty(value="课程开始时间")
    private Date scheduleStart;

    /**
     * 课程结束时间
     */
    //@ApiModelProperty(value="课程结束时间")
    private Date scheduleEnd;
}
