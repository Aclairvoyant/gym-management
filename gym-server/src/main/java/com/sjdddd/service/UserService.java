package com.sjdddd.service;

import com.sjdddd.dto.UserLoginDTO;
import com.sjdddd.dto.UserRegisterDTO;
import com.sjdddd.entity.User;

/**
 * @Author: 沈佳栋
 * @Description: TODO
 * @DateTime: 2023/11/20 10:56
 **/


public interface UserService {

    /**
     * 用户登录
     */
    User login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    User getUserInfo(Object userId);

    void update(User user);

    User findByUserId(Object userId);

    void updateAvatar(Long userId, String filePath);

}
