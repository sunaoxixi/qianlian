package com.sunnao.qianlian.modules.user.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyRequest {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

}
