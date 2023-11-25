package com.sjdddd.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:20
 **/

@Data
public class OrderListDTO implements java.io.Serializable{

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
