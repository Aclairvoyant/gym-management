package com.sjdddd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjdddd.entity.Course;
import com.sjdddd.entity.Order;
import com.sjdddd.mapper.BookingMapper;
import com.sjdddd.mapper.OrderMapper;
import com.sjdddd.mapper.PaymentMapper;
import com.sjdddd.result.PageResult;
import com.sjdddd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:11
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Order> list = orderMapper.selectAllOrders();

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();


        return new PageResult(total, list);

    }

    @Override
    public PageResult searchList(Integer pageNum, Integer pageSize, String courseName) {
        PageHelper.startPage(pageNum, pageSize);

        List<Order> list = orderMapper.searchOrders(courseName);

        PageInfo page = new PageInfo(list);

        long total = page.getTotal();

        return new PageResult(total, list);
    }


}
