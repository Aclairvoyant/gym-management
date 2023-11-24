package com.sjdddd.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 19:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAddVO implements java.io.Serializable{
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
