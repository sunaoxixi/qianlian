package com.sunnao.qianlian.modules.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
