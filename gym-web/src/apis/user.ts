import {AxiosPromise} from 'axios';
import request from '@/utils/request';

interface UserRegisterParams {
    userName: string;
    password: string;
    repassword: string;
    userRealName: string;
    userPhone: number;
    dateBirth: string;
    sex: number;
    avatar: string;
}

interface UserLoginParams {
    userName: string;
    password: string;
}

// interface UserInfo {
//     userName: string,
//     userRealName?: string,
//     sex?: string,
//     userPhone?: number,
//     avatar?: string
//
// }

interface UserUpdateInfoParams {
    id: number;
    nickname: string;
    email: string;
}

interface UserUpdateAvatarParams {
    avatar: string;
}

interface UserUpdatePasswordParams {
    old_pwd: string;
    new_pwd: string;
    re_pwd: string;
}

// 注册接口
export const userRegisterService = ({
                                        userName,
                                        password,
                                        repassword,
                                        userRealName,
                                        userPhone,
                                        dateBirth,
                                        sex,
                                        avatar
                                    }: UserRegisterParams): AxiosPromise =>
    request.post('/register', {userName, password, repassword, userRealName, userPhone, dateBirth, sex, avatar});

// 登录接口
export const userLoginService = ({userName, password}: UserLoginParams): AxiosPromise =>
    request.post('/login', {userName, password});

// 获取用户基本信息
export const userGetInfoService = (): AxiosPromise =>
    request.get('/userInfo');

// 更新用户基本信息
export const userUpdateInfoService = ({id, nickname, email}: UserUpdateInfoParams): AxiosPromise =>
    request.put('/userInfo', {id, nickname, email});

// 更新用户头像
export const userUpdateAvatarService = (avatar: string): AxiosPromise =>
    request.patch('/update/avatar', {avatar});

// 更新用户密码
export const userUpdatePasswordService = ({old_pwd, new_pwd, re_pwd}: UserUpdatePasswordParams): AxiosPromise =>
    request.patch('/updatepwd', {old_pwd, new_pwd, re_pwd});
