package com.sunnao.qianlian.modules.auth.service;

import com.sunnao.qianlian.modules.auth.model.request.AuthLoginRequest;
import com.sunnao.qianlian.modules.auth.model.request.AuthRegisterRequest;
import com.sunnao.qianlian.modules.auth.model.response.AuthLoginResponse;

public interface AuthService {

    /**
     * 注册
     *
     * @param authRegisterRequest 注册请求参数
     * @return 是否注册成功
     */
    boolean register(AuthRegisterRequest authRegisterRequest);

    /**
     * 登录
     *
     * @param authLoginRequest 登录请求参数
     * @return 登录结果
     */
    AuthLoginResponse login(AuthLoginRequest authLoginRequest);

    /**
     * 注销
     *
     * @return 是否注销成功
     */
    boolean logout();

}
