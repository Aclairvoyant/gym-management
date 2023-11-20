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
//@ApiModel(description="用户信息表")
public class User implements Serializable {

    /**
     * 用户id
     */
    //@ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 用户名
     */
    //@ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 用户密码
     */
    //@ApiModelProperty(value="用户密码")
    private String password;

    /**
     * 用户姓名
     */
    //@ApiModelProperty(value="用户姓名")
    private String userRealName;

    /**
     * 手机号
     */
    //@ApiModelProperty(value="手机号")
    private String userPhone;

    /**
     * 用户生日
     */
    //@ApiModelProperty(value="用户生日")
    private Date dateBirth;

    /**
     * 用户类型
     */
    //@ApiModelProperty(value="用户类型")
    private String userType;

    /**
     * 头像地址
     */
    //@ApiModelProperty(value="头像地址")
    private String avatar;

    /**
     * 性别，0 男， 1 女， 2未知
     */
    //@ApiModelProperty(value="性别，0 男， 1 女， 2未知")
    private String sex;

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
