package com.sjdddd.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 10:08
 **/
@Data
public class CoachEditDTO implements Serializable {

    private Long coachId;

    private String coachRealName;

    private String coachSex;

    private String coachPhone;

    private String coachRemark;
}
