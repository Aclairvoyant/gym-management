package com.sjdddd.entity;

import java.math.BigDecimal;
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
    * 会员表
    */
//@ApiModel(description="会员表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCard {
    /**
    * 会员卡号
    */
    //@ApiModelProperty(value="会员卡号")
    private Long memberCardId;

    /**
    * 用户id
    */
    //@ApiModelProperty(value="用户id")
    private Long userId;

    /**
    * 头像地址
    */
    //@ApiModelProperty(value="头像地址")
    private String avatar;

    /**
    * 会员余额
    */
    //@ApiModelProperty(value="会员余额")
    private BigDecimal memberFee;

    /**
    * 会员卡状态，0 未激活， 1 已激活， 2 已过期
    */
    //@ApiModelProperty(value="会员卡状态，0 未激活， 1 已激活， 2 已过期")
    private String memberCardStatus;

    /**
    * 激活日期
    */
    //@ApiModelProperty(value="激活日期")
    private Date activateTime;

    /**
    * 过期日期
    */
    //@ApiModelProperty(value="过期日期")
    private Date expireTime;

    /**
    * 创建时间
    */
    //@ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新时间
    */
    //@ApiModelProperty(value="更新时间")
    private Date updateTime;
}
