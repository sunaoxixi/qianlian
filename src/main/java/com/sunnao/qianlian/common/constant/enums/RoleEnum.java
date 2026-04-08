package com.sunnao.qianlian.common.constant.enums;

import com.sunnao.qianlian.common.base.IBaseEnum;
import lombok.Getter;

@Getter
public enum RoleEnum implements IBaseEnum<Integer> {

    USER(1, "USER"),
    ADMIN(0, "ADMIN");

    private final Integer code;

    private final String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取系统默认角色编码
     *
     * @return 系统默认的角色编码
     */
    public static Integer getDefaultRoleCode() {
        return USER.code;
    }
}
