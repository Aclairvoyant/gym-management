package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 沈佳栋
 * @Description: 教练信息
 * @DateTime: 2023/10/24 21:05
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coach implements Serializable {

    //教练id
    private Long CoachId;

    //教练姓名
    private String CoachName;

    //教练性别
    private String CoachSex;

    //教练年龄
    private String CoachAge;

    //教练手机号码
    private String CoachPhone;

    //教练专长或授课类型
    private String CoachSpecialty;
}
