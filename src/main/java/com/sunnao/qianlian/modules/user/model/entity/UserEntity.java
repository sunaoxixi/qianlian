package com.sunnao.qianlian.modules.user.model.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.mybatisflex.annotation.Table;
import com.sunnao.qianlian.common.base.BaseEntity;
import com.sunnao.qianlian.common.constant.enums.RoleEnum;
import com.sunnao.qianlian.common.constant.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("sys_user")
public class UserEntity extends BaseEntity {

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 状态((1-正常 0-禁用)
     */
    private Integer status;

    /**
     * 创建人 ID
     */
    private Long createBy;

    /**
     * 更新人 ID
     */
    private Long updateBy;

    /**
     * 初始化用户信息（在创建新用户时调用）
     */
    public void init() {
        if (StrUtil.isBlankIfStr(nickname)) {
            nickname = email;
        }
        if (StrUtil.isNotBlank(password)) {
            password = SecureUtil.md5(password);
        }
        if (BeanUtil.isEmpty(role)) {
            role = RoleEnum.getDefaultRoleCode();
        }
        if (BeanUtil.isEmpty(status)) {
            status = StatusEnum.getDefaultStatusCode();
        }
    }
}
