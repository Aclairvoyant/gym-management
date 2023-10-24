package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: 沈佳栋
 * @Description: 支付信息
 * @DateTime: 2023/10/24 21:10
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    /**
     * 支付状态 0未支付 1已支付 2退款
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    //支付id
    private Long PaymentId;

    //用户id
    private Long UserId;

    //课程id
    private Long CourseId;

    //支付时间
    private LocalDateTime PaymentTime;

    //支付金额
    private BigDecimal PaymentAmount;

    //支付状态
    private Integer PaymentStatus;

    //支付方式
    private Integer PaymentMethod;

}
