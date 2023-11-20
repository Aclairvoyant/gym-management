package com.sjdddd.mapper;

import com.sjdddd.entity.MemberCard;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:32
**/
@Mapper
public interface MemberCardMapper {
    int deleteByPrimaryKey(Long memberCardId);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Long memberCardId);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);
}
