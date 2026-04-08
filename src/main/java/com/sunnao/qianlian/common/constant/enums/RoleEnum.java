package com.sunnao.qianlian.common.constant.enums;

import com.sunnao.qianlian.common.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

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

    /**
     * 根据角色code获取角色desc
     *
     * @param code 角色code
     */
    public static String getRoleDescByCode(Integer code) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> role.getCode().equals(code))
                .map(RoleEnum::getDesc)
                .findFirst()
                .orElse(null);
    }

}
