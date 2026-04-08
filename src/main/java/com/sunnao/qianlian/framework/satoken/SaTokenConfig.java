package com.sunnao.qianlian.framework.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.sunnao.qianlian.common.model.KeyValue;
import com.sunnao.qianlian.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * satoken相关配置
 *
 * @author sunnao
 */
@Configuration
@RequiredArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer, StpInterface {

    private final UserService userService;

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能 
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本项目不需要权限码鉴权, 所以这里直接返回空数组
        return CollUtil.newArrayList();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     *
     * @param loginId   用户id
     * @param loginType 登陆类型
     * @return 登陆用户的角色desc列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        KeyValue<Integer> role = userService.queryUserRole((Long) loginId);
        return CollUtil.newArrayList(role.getValue());
    }

}
