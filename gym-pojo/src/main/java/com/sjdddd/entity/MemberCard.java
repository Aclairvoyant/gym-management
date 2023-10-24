package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 沈佳栋
 * @Description: 会员卡信息
 * @DateTime: 2023/10/24 21:02
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCard implements Serializable {

    /**
     * 会员卡状态 0-未激活 1-已激活 2-已过期
     */
    public static final Integer UN_ACTIVATED = 0;
    public static final Integer ACTIVATED = 1;
    public static final Integer EXPIRED = 2;

    //会员卡id
    private Long MemberCardId;

    //用户id
    private Long UserId;

    //会员头像
    private String MemberCardAvatar;

    //激活时间
    private LocalDateTime MemberCardActivationTime;

    //过期时间
    private LocalDateTime MemberCardExpirationTime;

    //会员卡状态
    private Integer MemberCardStatus;

    //创建时间
    private LocalDateTime MemberCardCreateTime;

    //更新时间
    private LocalDateTime MemberCardUpdateTime;

    //会员卡类型
    //private String MemberCardType;

    //会员卡余额
    //private String MemberCardBalance;
}
