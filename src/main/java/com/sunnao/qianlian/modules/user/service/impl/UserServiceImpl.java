package com.sunnao.qianlian.modules.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sunnao.qianlian.common.constant.enums.RoleEnum;
import com.sunnao.qianlian.common.model.KeyValue;
import com.sunnao.qianlian.common.result.ResultCode;
import com.sunnao.qianlian.modules.user.converter.UserConverter;
import com.sunnao.qianlian.modules.user.mapper.UserMapper;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.entity.table.UserTable;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.model.request.UserModifyRequest;
import com.sunnao.qianlian.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final UserConverter userConverter;

    /**
     * 创建用户
     *
     * @param userCreateRequest 创建用户请求参数
     * @return 是否创建成功
     */
    @Override
    public boolean create(UserCreateRequest userCreateRequest) {
        // 检查邮箱是否已存在
        Assert.isFalse(checkEmailExists(userCreateRequest.getEmail()), ResultCode.EMAIL_ALREADY_EXISTS.getMsg());

        // 调用数据库保存用户
        UserEntity userEntity = userConverter.convertToEntity(userCreateRequest);
        userEntity.init();

        return save(userEntity);
    }

    /**
     * 修改用户
     *
     * @param userModifyRequest 修改用户请求
     * @return 是否修改成功
     */
    @Override
    public boolean modify(UserModifyRequest userModifyRequest) {

        UserEntity userEntity = userConverter.convertToEntity(userModifyRequest);

        return updateById(userEntity);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 是否删除成功
     */
    @Override
    public boolean delete(Long userId) {
        return removeById(userId);
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    @Override
    public UserEntity queryByEmail(String email) {
        QueryWrapper queryWrapper = QueryWrapper.create().where(UserTable.USER.EMAIL.eq(email));
        return getOne(queryWrapper);
    }

    /**
     * 获取用户角色键值对
     *
     * @param userId 用户id
     * @return 对应的用户角色键值对
     */
    @Override
    public KeyValue<Integer> queryUserRole(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(UserTable.USER.ROLE).where(UserTable.USER.ID.eq(userId));

        Integer roleCode = getOneAs(queryWrapper, Integer.class);

        return KeyValue
                .<Integer>builder()
                .key(roleCode)
                .value(RoleEnum.getRoleDescByCode(roleCode))
                .build();
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 传入的邮箱是否存在
     */
    @Override
    public boolean checkEmailExists(String email) {
        return checkEmailExists(email, null);
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email     邮箱
     * @param excludeId 排除的ID
     * @return 传入的邮箱是否存在
     */
    @Override
    public boolean checkEmailExists(String email, Long excludeId) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .where(UserTable.USER.EMAIL.eq(email))
                .and(UserTable.USER.ID.ne(excludeId));
        return exists(queryWrapper);
    }
}
