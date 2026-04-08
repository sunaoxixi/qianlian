package com.sunnao.qianlian.modules.user.controller;

import com.sunnao.qianlian.common.result.Result;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.model.request.UserModifyRequest;
import com.sunnao.qianlian.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/modify")
    public Result<Void> modify(@RequestBody @Valid UserModifyRequest userModifyRequest) {
        boolean modifyResult = userService.modify(userModifyRequest);
        return Result.judge(modifyResult);
    }

    @DeleteMapping("/delete/{userId}")
    public Result<Void> delete(@PathVariable Long userId) {
        boolean deleteResult = userService.delete(userId);
        return Result.judge(deleteResult);
    }

}
