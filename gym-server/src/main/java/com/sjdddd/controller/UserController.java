package com.sjdddd.controller;

import com.sjdddd.constant.JwtClaimsConstant;
import com.sjdddd.dto.UserLoginDTO;
import com.sjdddd.dto.UserRegisterDTO;
import com.sjdddd.entity.User;
import com.sjdddd.properties.JwtProperties;
import com.sjdddd.result.Result;
import com.sjdddd.service.UserService;
import com.sjdddd.utils.JwtUtil;
import com.sjdddd.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO.getUserName());

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getUserId())
                .userName(user.getUserName())
                .token(token)
                .build();

        return Result.success(userLoginVO);

    }

    @CrossOrigin
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}", userRegisterDTO.getUserName());

        User user = userService.register(userRegisterDTO);

        return Result.success(user);
    }
}
