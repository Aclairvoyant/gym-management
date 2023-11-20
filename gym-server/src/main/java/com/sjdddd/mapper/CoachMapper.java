package com.sjdddd.mapper;

import com.sjdddd.entity.Coach;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:32
**/
@Mapper
public interface CoachMapper {
    int deleteByPrimaryKey(Long coachId);

    int insert(Coach record);

    int insertSelective(Coach record);

    Coach selectByPrimaryKey(Long coachId);

    int updateByPrimaryKeySelective(Coach record);

    int updateByPrimaryKey(Coach record);
}
