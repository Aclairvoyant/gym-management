package com.sjdddd.dto;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/23 09:54
 **/

@Data
public class MemberEditDTO implements java.io.Serializable{
    private Long userId;

    private String userName;

    private String userRealName;

    private String userPhone;

    private String sex;

    private Date dateBirth;

    private BigDecimal memberFee;

    private String memberCardStatus;
}
