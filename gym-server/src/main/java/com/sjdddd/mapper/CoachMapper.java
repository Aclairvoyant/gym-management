package com.sjdddd.mapper;

import com.sjdddd.annotation.AutoFill;
import com.sjdddd.entity.Coach;
import com.sjdddd.entity.MemberCard;
import com.sjdddd.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 16:32
 **/
@Mapper
public interface CoachMapper {
    int deleteByPrimaryKey(Long coachId);

    @AutoFill(value = OperationType.INSERT)
    int insert(Coach record);

    @AutoFill(value = OperationType.INSERT)
    int insertSelective(Coach record);

    Coach selectByPrimaryKey(Long coachId);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKeySelective(Coach record);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKey(Coach record);

    List<Coach> selectAll();

    List<Coach> selectByCoachRealName(String coachRealName);

    List<Coach> selectCoachRealName();
}
