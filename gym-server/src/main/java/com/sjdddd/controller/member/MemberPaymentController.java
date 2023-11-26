package com.sjdddd.controller.member;

import com.sjdddd.annotation.OperationLog;
import com.sjdddd.constant.JwtClaimsConstant;
import com.sjdddd.entity.User;
import com.sjdddd.properties.JwtProperties;
import com.sjdddd.result.PageResult;
import com.sjdddd.result.Result;
import com.sjdddd.service.CourseService;
import com.sjdddd.service.MemberService;
import com.sjdddd.service.OrderService;
import com.sjdddd.service.PaymentService;
import com.sjdddd.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/26 15:56
 **/

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/member")
public class MemberPaymentController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OrderService orderService;

    // 余额查询
    @GetMapping("/balance")
    @OperationLog(operDesc = "查询余额")
    public Result<BigDecimal> balance(
            HttpServletRequest request
    ) {

        String token = request.getHeader(jwtProperties.getUserTokenName());

        log.info("token:{}", token);

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);

        log.info("userId:{}", userId);

        BigDecimal balance = memberService.getMemberFee(userId);

        return Result.success(balance);

    }


    @PostMapping("/payCourseFee")
    @OperationLog(operDesc = "支付课程费用")
    public Result<?> payCourseFee(
            HttpServletRequest request,
            @RequestParam Long courseId
    ) {

        String token = request.getHeader(jwtProperties.getUserTokenName());

        log.info("token:{}", token);

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);


        // 实现支付逻辑
        // 这可能包括检查用户余额、减少用户余额、创建支付记录等
        boolean success = paymentService.processPayment(userId, courseId);

        if (success) {
            // 支付成功，返回适当的响应
            return Result.success("支付成功");
        } else {
            // 支付失败，返回错误响应
            return Result.error("支付失败");
        }
    }

    @PostMapping("/unenroll")
    @OperationLog(operDesc = "取消报名,退课")
    public Result<?> unenroll(
            HttpServletRequest request,
            @RequestParam Long courseId
    ) {

        String token = request.getHeader(jwtProperties.getUserTokenName());

        log.info("token:{}", token);

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);

        // 执行退课逻辑
        boolean isUnenrolled = courseService.unenrollCourse(userId, courseId);

        if (isUnenrolled) {
            // 执行退款逻辑
            paymentService.refund(userId, courseId);

            return Result.success("课程退订成功");
        } else {
            return Result.error("课程退订失败");
        }
    }

    @GetMapping("/bill/list")
    @OperationLog(operDesc = "会员查询账单列表")
    public Result<PageResult> orderList(
            Integer pageNum,
            Integer pageSize,
            HttpServletRequest request
    ) {
        String token = request.getHeader(jwtProperties.getUserTokenName());

        log.info("token:{}", token);

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);

        PageResult pageResult = orderService.listMemberBills(pageNum, pageSize, userId);

        return Result.success(pageResult);
    }
}
