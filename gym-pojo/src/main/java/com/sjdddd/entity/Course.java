package com.sjdddd.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    * 课程信息表
    */
//@ApiModel(description="课程信息表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
    * 课程id
    */
    //@ApiModelProperty(value="课程id")
    private Long courseId;

    /**
    * 课程名
    */
    //@ApiModelProperty(value="课程名")
    private String courseName;

    /**
    * 教练id
    */
    //@ApiModelProperty(value="教练id")
    private Long coachId;

    /**
     * 教练姓名
     */
    //@ApiModelProperty(value="教练姓名")
    private String coachRealName;

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

    /**
    * 课程金额
    */
    //@ApiModelProperty(value="课程金额")
    private BigDecimal courseFee;

    /**
     * 创建时间
     */
    //@ApiModelProperty(value="创建时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    //@ApiModelProperty(value="更新时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 课程状态
     */
    //@ApiModelProperty(value="课程状态")
    private String isEnrolled;
}
