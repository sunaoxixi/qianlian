package com.sunnao.qianlian.modules.user.service;

import com.mybatisflex.core.service.IService;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;

public interface UserService extends IService<UserEntity> {

    /**
     * 创建用户
     *
     * @param userCreateRequest 创建用户请求参数
     * @return 是否创建成功
     */
    boolean create(UserCreateRequest userCreateRequest);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 传入的邮箱是否存在
     */
    boolean checkEmailExists(String email);

    /**
     * 检查邮箱是否存在
     *
     * @param email     邮箱
     * @param excludeId 排除的ID
     * @return 传入的邮箱是否存在
     */
    boolean checkEmailExists(String email, Long excludeId);

}
