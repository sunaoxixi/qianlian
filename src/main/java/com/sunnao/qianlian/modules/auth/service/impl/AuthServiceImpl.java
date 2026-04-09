package com.sunnao.qianlian.modules.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sunnao.qianlian.common.exception.BusinessException;
import com.sunnao.qianlian.common.result.ResultCode;
import com.sunnao.qianlian.modules.auth.converter.AuthConverter;
import com.sunnao.qianlian.modules.auth.model.request.AuthLoginRequest;
import com.sunnao.qianlian.modules.auth.model.request.AuthRegisterRequest;
import com.sunnao.qianlian.modules.auth.model.response.AuthLoginResponse;
import com.sunnao.qianlian.modules.auth.service.AuthService;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthConverter authConverter;

    private final UserService userService;

    /**
     * 注册
     *
     * @param authRegisterRequest 注册请求参数
     * @return 是否注册成功
     */
    @Override
    public boolean register(AuthRegisterRequest authRegisterRequest) {
        if (!StrUtil.equals(authRegisterRequest.getPassword(), authRegisterRequest.getConfirmPassword())) {
            throw new BusinessException(ResultCode.USER_REGISTRATION_ERROR);
        }
        if (userService.checkEmailExists(authRegisterRequest.getEmail())) {
            throw new BusinessException(ResultCode.EMAIL_ALREADY_EXISTS);
        }
        UserCreateRequest userCreateRequest = authConverter.convertToUserCreateRequest(authRegisterRequest);
        return userService.create(userCreateRequest);
    }

    /**
     * 登录
     *
     * @param authLoginRequest 登录请求参数
     * @return 登录结果
     */
    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        UserEntity userEntity = userService.queryByEmail(authLoginRequest.getEmail());
        if (ObjectUtil.isNull(userEntity)) {
            throw new BusinessException(ResultCode.EMAIL_NOT_FOUND);
        }
        // 检查用户是否被禁用
        if (userEntity.isDisabled()) {
            throw new BusinessException(ResultCode.EMAIL_FROZEN);
        }
        if (!userEntity.matchPassword(authLoginRequest.getPassword())) {
            throw new BusinessException(ResultCode.EMAIL_PASSWORD_ERROR);
        }
        StpUtil.login(userEntity.getId());
        return AuthLoginResponse.builder().accessToken(StpUtil.getTokenValue()).build();
    }

    /**
     * 注销
     *
     * @return 是否注销成功
     */
    @Override
    public boolean logout() {
        StpUtil.logout();
        return true;
    }

}
