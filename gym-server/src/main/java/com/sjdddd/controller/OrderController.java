package com.sjdddd.controller;

import com.sjdddd.dto.OrderListDTO;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/25 23:10
 **/

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result<PageResult> orderList(
            Integer pageNum,
            Integer pageSize
    ) {

        PageResult pageResult = orderService.list(pageNum, pageSize);

        return Result.success(pageResult);
    }

    @GetMapping("/search")
    public Result<PageResult> searchCourseList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String courseName
    ) {

        PageResult pageResult = orderService.searchList(pageNum, pageSize, courseName);

        return Result.success(pageResult);

    }
}
