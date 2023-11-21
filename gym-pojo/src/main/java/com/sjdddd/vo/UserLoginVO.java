package com.sjdddd.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 10:51
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements java.io.Serializable{

    private Long id;

    private String userName;

    private String password;

    private String token;
}
