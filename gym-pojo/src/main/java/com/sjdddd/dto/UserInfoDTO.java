package com.sjdddd.dto;

import lombok.Data;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/22 13:26
 **/
@Data
public class UserInfoDTO implements java.io.Serializable{
    private Long userId;

    private String userName;

    private String userRealName;

    private String userPhone;

    private String userSex;


}
