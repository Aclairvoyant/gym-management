package com.sjdddd.service.impl;

import com.sjdddd.constant.MessageConstant;
import com.sjdddd.dto.UserLoginDTO;
import com.sjdddd.dto.UserRegisterDTO;
import com.sjdddd.entity.User;
import com.sjdddd.exception.BaseException;
import com.sjdddd.mapper.UserMapper;
import com.sjdddd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountLockedException;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 10:57
 **/

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUserName();
        String password = userLoginDTO.getPassword();

        //log.info(password);

        User user = userMapper.selectByUserName(username);

        if (user == null) {
            // 账号不存在
            throw new BaseException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //log.info(password);

        //密码比对
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new BaseException(MessageConstant.PASSWORD_ERROR);
        }

        return user;
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
//        String username = userRegisterDTO.getUserName();
//        String password = userRegisterDTO.getPassword();
//        String userRealName = userRegisterDTO.getUserRealName();
//
//        User user = userMapper.selectByUserName(username);
//
//        if (user != null) {
//            // 账号已存在
//            throw new BaseException(MessageConstant.ACCOUNT_EXIST);
//        }
//
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//
//        User newUser = new User();
//        newUser.setUserName(username);
//        newUser.setPassword(password);
//
//        userMapper.insertSelective(newUser);

        User user = new User();

        BeanUtils.copyProperties(userRegisterDTO, user);

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        userMapper.insertSelective(user);

        return user;
    }
}