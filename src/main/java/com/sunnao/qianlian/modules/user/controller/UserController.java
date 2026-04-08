package com.sunnao.qianlian.modules.user.controller;

import com.sunnao.qianlian.common.result.Result;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        boolean createResult = userService.create(userCreateRequest);
        return Result.judge(createResult);
    }
}
