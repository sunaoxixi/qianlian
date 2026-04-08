package com.sunnao.qianlian.modules.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sunnao.qianlian.common.result.ResultCode;
import com.sunnao.qianlian.modules.user.converter.UserConverter;
import com.sunnao.qianlian.modules.user.mapper.UserMapper;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.entity.table.UserTable;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
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
