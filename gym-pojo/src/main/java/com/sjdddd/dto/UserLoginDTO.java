package com.sjdddd.dto;

import lombok.Data;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 10:53
 **/

@Data
public class UserLoginDTO implements java.io.Serializable{
    private String userName;

    private String password;
}
