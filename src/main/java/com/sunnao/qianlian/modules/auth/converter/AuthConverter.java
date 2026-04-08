package com.sunnao.qianlian.modules.auth.converter;

import com.sunnao.qianlian.modules.auth.model.request.AuthRegisterRequest;
import com.sunnao.qianlian.modules.user.model.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthConverter {

    UserCreateRequest convertToUserCreateRequest(AuthRegisterRequest authRegisterRequest);

}
