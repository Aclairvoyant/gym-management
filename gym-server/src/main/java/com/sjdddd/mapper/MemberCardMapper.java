package com.sjdddd.mapper;

import com.github.pagehelper.Page;
import com.sjdddd.annotation.AutoFill;
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
public interface MemberCardMapper {
    int deleteByPrimaryKey(Long memberCardId);

    @AutoFill(value = OperationType.INSERT)
    int insert(MemberCard record);

    @AutoFill(value = OperationType.INSERT)
    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Long memberCardId);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKeySelective(MemberCard record);

    @AutoFill(value = OperationType.UPDATE)
    int updateByPrimaryKey(MemberCard record);

    List<MemberCard> selectAll();

    //List<MemberCard> selectAll2();

    List<MemberCard> selectByUserRealName(String userRealName);
}
