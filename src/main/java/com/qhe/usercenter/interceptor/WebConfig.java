package com.qhe.usercenter.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebConfig 用于注册权限拦截器
 *
 * @author IamQhe
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，添加拦截路径
        // addPathPatterns: /* 为一级目录； /** 为多级目录
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**",
                        "/*/admin/**",
                        "/**/admin/**");
    }
}
