package com.sunnao.qianlian.modules.user.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
