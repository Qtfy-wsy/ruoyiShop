package com.ruoyi.config;

import com.ruoyi.interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 魔金商城 on 2019/5/13.
 * 拦截器配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 注入登录拦截器
     */
    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor).excludePathPatterns("/v2/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**");
    }
}
