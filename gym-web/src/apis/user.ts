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

interface UserInfo {
    userName: string,
    userRealName?: string,
    sex?: string,
    userPhone?: string,
    avatar?: string

}

interface UserUpdateInfoParams {
    userId: string,
    userRealName: string,
    userPhone: string,
    sex: number,
    dateBirth: Date
}

interface UserUpdateAvatarParams {
    avatar: string;
}

interface UserUpdatePasswordParams {
    userId: string;
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
export const userUpdateInfoService = ({userId, userRealName, userPhone, sex, dateBirth}: UserUpdateInfoParams): AxiosPromise =>
    request.put('/userInfo', {userId, userRealName, userPhone, sex, dateBirth});

// 更新用户头像
export const userUpdateAvatarService = (formData: FormData): AxiosPromise => {
    return request.post('/upload/avatar', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
};

// 更新用户密码
export const userUpdatePasswordService = ({userId, old_pwd, new_pwd, re_pwd}: UserUpdatePasswordParams): AxiosPromise =>
    request.patch('/updatePwd', {userId, old_pwd, new_pwd, re_pwd});

// 获取用户余额
export const userGetBalanceService = (): AxiosPromise =>
    request.get('/member/balance');
