package com.sjdddd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/10/24 21:54
 **/

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
