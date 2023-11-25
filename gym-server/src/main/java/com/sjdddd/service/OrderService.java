package com.sjdddd.service;

import com.sjdddd.dto.OrderListDTO;
import com.sjdddd.result.PageResult;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:11
 **/
public interface OrderService {

    PageResult list(Integer pageNum, Integer pageSize);

    PageResult searchList(Integer pageNum, Integer pageSize, String courseName);
}
