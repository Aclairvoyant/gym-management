package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: 沈佳栋
 * @Description: 用户信息
 * @DateTime: 2023/10/24 20:55
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    //用户id
    private Long UserId;

    //用户名
    private String UserName;

    //用户密码
    private String UserPassword;

    //用户真名
    private String UserRealName;

    //用户手机号码
    private String UserPhone;

    //出生日期
    private Date UserDateOfBirth;

    //用户角色
    private String UserRole;

    //用户头像
    private String UserAvatar;

    //用户性别
    private String UserSex;

    //注册时间
    private LocalDateTime UserCreateTime;

}
