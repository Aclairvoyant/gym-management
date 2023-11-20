package com.sjdddd.mapper;

import com.sjdddd.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:31
**/
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String userName);
}
