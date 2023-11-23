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
 * @DateTime: 2023/11/22 23:18
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddVO implements java.io.Serializable {

    private Long userId;

    private String userName;

    private String userRealName;

    private String userPhone;

    private String sex;

    private Date dateBirth;

    private BigDecimal memberFee;

    private String memberCardStatus;

}
