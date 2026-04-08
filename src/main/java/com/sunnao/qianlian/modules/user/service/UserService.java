package com.sunnao.qianlian.modules.user.service;

import com.mybatisflex.core.service.IService;
import com.sunnao.qianlian.common.model.KeyValue;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.model.request.UserModifyRequest;
import jakarta.validation.Valid;

public interface UserService extends IService<UserEntity> {

    /**
     * 创建用户
     *
     * @param userCreateRequest 创建用户请求参数
     * @return 是否创建成功
     */
    boolean create(UserCreateRequest userCreateRequest);

    /**
     * 修改用户
     *
     * @param userModifyRequest 修改用户请求
     * @return 是否修改成功
     */
    boolean modify(@Valid UserModifyRequest userModifyRequest);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 是否删除成功
     */
    boolean delete(Long userId);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    UserEntity queryByEmail(String email);

    /**
     * 获取用户的角色信息
     *
     * @param userId 用户id
     * @return key-角色code value-角色desc
     */
    KeyValue<Integer> queryUserRole(Long userId);

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
