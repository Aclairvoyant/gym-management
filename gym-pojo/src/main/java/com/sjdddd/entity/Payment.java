package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:31
**/
/**
    * 支付表
    */
//@ApiModel(description="支付表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    /**
    * 支付id
    */
    //@ApiModelProperty(value="支付id")
    private Long paymentId;

    /**
    * 用户id
    */
    //@ApiModelProperty(value="用户id")
    private Long userId;

    /**
    * 支付金额
    */
    //@ApiModelProperty(value="支付金额")
    private BigDecimal amount;

    /**
    * 支付日期时间
    */
    //@ApiModelProperty(value="支付日期时间")
    private Date paymentDate;

    /**
    * 支付方式
    */
    //@ApiModelProperty(value="支付方式")
    private String paymentType;

}
