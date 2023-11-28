package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 18:20
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    private Long paymentId;

    private Long bookingId;

    private Object userId;

    private Date paymentDate;

    private BigDecimal amount;

    private String paymentType;

    private BigDecimal memberFee;

    private String courseName;

}
