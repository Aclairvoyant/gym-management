package com.sjdddd.mapper;

import com.sjdddd.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 沈佳栋
* @Description: TODO
* @DateTime: 2023/11/20 16:31
**/
@Mapper
public interface PaymentMapper {
    int deleteByPrimaryKey(Long paymentId);

    int insert(Payment record);

    int insertSelective(Payment record);

    Payment selectByPrimaryKey(Long paymentId);

    int updateByPrimaryKeySelective(Payment record);

    int updateByPrimaryKey(Payment record);
}
