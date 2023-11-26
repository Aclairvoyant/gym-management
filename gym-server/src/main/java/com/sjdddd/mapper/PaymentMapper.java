package com.sjdddd.mapper;

import com.sjdddd.annotation.AutoFill;
import com.sjdddd.entity.Payment;
import com.sjdddd.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 16:31
 **/
@Mapper
public interface PaymentMapper {
    int deleteByPrimaryKey(Long paymentId);

    //@AutoFill(OperationType.INSERT)
    int insert(Payment record);

    int insertSelective(Payment record);

    Payment selectByPrimaryKey(Long paymentId);

    int updateByPrimaryKeySelective(Payment record);

    int updateByPrimaryKey(Payment record);
}
