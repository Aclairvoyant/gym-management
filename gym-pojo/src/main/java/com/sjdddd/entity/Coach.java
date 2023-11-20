package com.sjdddd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:32
**/
/**
    * 教练信息表
    */
//@ApiModel(description="教练信息表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coach {
    /**
    * 教练id
    */
    //@ApiModelProperty(value="教练id")
    private Long coachId;

    /**
    * 性别，0 男， 1 女， 2未知
    */
    //@ApiModelProperty(value="性别，0 男， 1 女， 2未知")
    private String coachSex;

    /**
    * 教练姓名
    */
    //@ApiModelProperty(value="教练姓名")
    private String coachRealName;

    /**
    * 手机号
    */
    //@ApiModelProperty(value="手机号")
    private String coachPhone;

    /**
    * 教练专长或备注
    */
    //@ApiModelProperty(value="教练专长或备注")
    private String coachRemark;
}
