package com.sunnao.qianlian.modules.user.converter;

import com.sunnao.qianlian.modules.user.model.entity.UserEntity;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import com.sunnao.qianlian.modules.user.model.request.UserModifyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConverter {

    UserEntity convertToEntity(UserCreateRequest userCreateRequest);

    UserEntity convertToEntity(UserModifyRequest userModifyRequest);

}
