package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 沈佳栋
 * @Description: 课程信息
 * @DateTime: 2023/10/24 21:06
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    //课程id
    private Long CourseId;

    //课程名称
    private String CourseName;

    //教练id
    private Long CoachId;

    //课程类型
    private String CourseType;

    //课程安排
    private String CourseSchedule;

    //课程费用
    private BigDecimal CourseCost;
}
