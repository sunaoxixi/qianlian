package com.sunnao.qianlian.modules.auth.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthLoginResponse {

    /**
     * token
     */
    private String accessToken;

}
