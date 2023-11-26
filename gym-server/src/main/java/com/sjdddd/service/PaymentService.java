package com.sjdddd.service;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 16:19
 **/
public interface PaymentService {
    boolean processPayment(Object userId, Long courseId);

    void refund(Object userId, Long courseId);
}
