package com.sjdddd.mapper;

import com.sjdddd.entity.Bill;
import com.sjdddd.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:53
 **/
@Mapper
public interface OrderMapper {

    List<Order> selectAllOrders();

    List<Order> searchOrders(String courseName);

    Long countTotalOrders();

    Long countSearchedOrders(String courseName);


    List<Bill> selectByUserIdAllOrders(Object userId);
}
