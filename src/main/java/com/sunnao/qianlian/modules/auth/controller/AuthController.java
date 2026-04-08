package com.sunnao.qianlian.modules.auth.controller;

import com.sunnao.qianlian.common.result.Result;
import com.sunnao.qianlian.modules.auth.model.request.AuthLoginRequest;
import com.sunnao.qianlian.modules.auth.model.request.AuthRegisterRequest;
import com.sunnao.qianlian.modules.auth.model.response.AuthLoginResponse;
import com.sunnao.qianlian.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid AuthRegisterRequest authRegisterRequest) {
        boolean registerResult = authService.register(authRegisterRequest);
        return Result.judge(registerResult);
    }

    @PostMapping("/login")
    public Result<AuthLoginResponse> login(@RequestBody @Valid AuthLoginRequest authLoginRequest) {
        AuthLoginResponse authLoginResponse = authService.login(authLoginRequest);
        return Result.success(authLoginResponse);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        boolean logoutResult = authService.logout();
        return Result.judge(logoutResult);
    }

}
