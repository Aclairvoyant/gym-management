package com.sjdddd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseWithEnrollmentStatusDTO implements java.io.Serializable{
    //private Long userId;

    private Long courseId;

    private String courseName;

    private Long coachId;

    private String coachRealName;

    private BigDecimal courseFee;

    private Date scheduleStart;

    private Date scheduleEnd;

    private String isEnrolledByCurrentUser;

    private String isEnrolledByOther;

    private String isEnrolled;

}
