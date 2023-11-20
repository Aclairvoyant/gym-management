package com.sjdddd.mapper;

import com.sjdddd.entity.Booking;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:32
**/
@Mapper
public interface BookingMapper {
    int deleteByPrimaryKey(Long bookingId);

    int insert(Booking record);

    int insertSelective(Booking record);

    Booking selectByPrimaryKey(Long bookingId);

    int updateByPrimaryKeySelective(Booking record);

    int updateByPrimaryKey(Booking record);
}
