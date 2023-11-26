package com.sjdddd.service.impl;

import com.sjdddd.entity.Booking;
import com.sjdddd.entity.Payment;
import com.sjdddd.mapper.BookingMapper;
import com.sjdddd.mapper.CourseMapper;
import com.sjdddd.mapper.MemberCardMapper;
import com.sjdddd.mapper.PaymentMapper;
import com.sjdddd.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 16:19
 **/

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    @Transactional
    public boolean processPayment(Object userId, Long courseId) {
        log.info("userId:{}, courseId:{}", userId, courseId);

        BigDecimal balance = memberCardMapper.selectByUserId(userId);
        BigDecimal courseFee = courseMapper.selectCoursePrice(courseId);

        if (balance.compareTo(courseFee) >= 0) {
            // 用户余额足够，处理支付
            Booking booking = new Booking();
            booking.setCourseId(courseId);
            booking.setUserId(userId);
            booking.setBookingDate(new java.util.Date());
            log.info("booking:{}", booking);
            bookingMapper.insert(booking);

            // 更新用户余额
            BigDecimal newBalance = balance.subtract(courseFee);
            memberCardMapper.updateBalanceByUserId(userId, newBalance);

            // 记录支付信息
            Payment payment = new Payment();
            payment.setUserId(userId);
            payment.setAmount(courseFee);
            payment.setPaymentType("余额支付");
            payment.setPaymentStatus("1");
            payment.setPaymentDate(new java.util.Date());
            paymentMapper.insert(payment);

            return true;
        } else {
            // 余额不足
            return false;
        }
    }

    @Override
    public void refund(Object userId, Long courseId) {
        // 退款
        BigDecimal courseFee = courseMapper.selectCoursePrice(courseId);
        BigDecimal balance = memberCardMapper.selectByUserId(userId);
        BigDecimal newBalance = balance.add(courseFee);
        memberCardMapper.updateBalanceByUserId(userId, newBalance);

        // 记录退款信息
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(courseFee);
        payment.setPaymentType("余额退款");
        payment.setPaymentStatus("2");
        payment.setPaymentDate(new java.util.Date());
        paymentMapper.insert(payment);
    }
}
