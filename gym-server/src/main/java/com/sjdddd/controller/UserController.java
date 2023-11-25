package com.sjdddd.controller;

import com.sjdddd.constant.JwtClaimsConstant;
import com.sjdddd.constant.MessageConstant;
import com.sjdddd.dto.UserLoginDTO;
import com.sjdddd.dto.UserRegisterDTO;
import com.sjdddd.entity.User;
import com.sjdddd.properties.JwtProperties;
import com.sjdddd.result.Result;
import com.sjdddd.service.UserService;
import com.sjdddd.utils.JwtUtil;
import com.sjdddd.utils.ThreadLocalUtil;
import com.sjdddd.vo.UserLoginVO;
import com.sjdddd.vo.UserRegisterVO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 10:49
 **/

@RestController
@Slf4j
@Tag(name = "用户相关接口")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    public static final String USER_TOKEN = "token";

    //@CrossOrigin
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO.getUserName());

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

//        UserLoginVO userLoginVO = UserLoginVO.builder()
//                .id(user.getUserId())
//                .userName(user.getUserName())
//                .token(token)
//                .build();

        return Result.success(token);

    }

    //@CrossOrigin
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}", userRegisterDTO.getUserName());

        userService.register(userRegisterDTO);

        return Result.success();
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(HttpServletRequest request) {

        String token = request.getHeader(jwtProperties.getUserTokenName());

        log.info("token:{}", token);

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);

        log.info("userId:{}", userId);

        User user = userService.getUserInfo(userId);

        return Result.success(user);
    }

    @PutMapping("/userInfo")
    public Result update(@RequestBody User user) {

        userService.update(user);

        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> params,
                                    @RequestHeader(USER_TOKEN) final String token) {

        final String oldPwd = params.get("old_pwd");
        final String newPwd = params.get("new_pwd");
        final String rePwd = params.get("re_pwd");
        //final String userId = params.get("userId");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }

        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

        log.info("claims:{}", claims);

        Object userId = claims.get(JwtClaimsConstant.USER_ID);

        log.info("userId:{}", userId);

        final User user = userService.findByUserId(userId);

        log.info("user:{}", user);

        if (!rePwd.equals(newPwd)) {
            return Result.error("两次结果不一样");
        }
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))) {
            return Result.error(MessageConstant.PASSWORD_ERROR);
        }

        user.setPassword(DigestUtils.md5DigestAsHex(newPwd.getBytes()));

        userService.update(user);

        return Result.success();
    }
}
