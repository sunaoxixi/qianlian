package com.sunnao.qianlian.common.constant.enums;

import com.sunnao.qianlian.common.base.IBaseEnum;
import lombok.Getter;

@Getter
public enum StatusEnum implements IBaseEnum<Integer> {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private final Integer code;

    private final String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Integer getDefaultStatusCode() {
        return ENABLE.code;
    }
}
