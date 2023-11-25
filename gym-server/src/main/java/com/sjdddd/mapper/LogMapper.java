package com.sjdddd.mapper;

import com.sjdddd.entity.Course;
import com.sjdddd.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/26 02:04
**/
@Mapper
public interface LogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> selectAll();

    List<Log> searchLogs(String query);
}
