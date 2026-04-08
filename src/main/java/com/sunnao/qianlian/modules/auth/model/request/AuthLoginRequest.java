package com.sunnao.qianlian.modules.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
