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
* @DateTime: 2023/11/25 23:20
**/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVO implements java.io.Serializable{

    private Long bookingId;

    private String courseName;

    private String userRealName;

    private String coachName;

    private Long paymentId;

    private String paymentStatus;

    private String paymentType;

    private BigDecimal amount;

    private Date paymentDate;

    private Date bookingDate;
}
