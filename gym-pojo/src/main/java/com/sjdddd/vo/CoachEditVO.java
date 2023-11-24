package com.sjdddd.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/24 10:09
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachEditVO implements java.io.Serializable{

    private String coachRealName;

    private String coachSex;

    private String coachPhone;

    private String coachRemark;
}
