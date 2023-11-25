package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:31
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable{

    private Long bookingId;

    private String courseName;

    private String userRealName;

    private Long paymentId;

    private String paymentStatus;

    private String paymentType;

    private BigDecimal amount;

    private Date paymentDate;

    private Date bookingDate;

    private String courseId;

    private String userId;

    private String coachRealName;

    public String getCoachRealName() {
        return coachRealName;
    }

    public void setCoachRealName(String coachRealName) {
        this.coachRealName = coachRealName;
    }
}
