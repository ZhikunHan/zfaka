package org.hantiv.zfaka.config;

import org.hantiv.zfaka.controller.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Zhikun Han
 * @Date Created in 16:47 2022/7/13
 * @Description:
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/order/token", "/order/create");
    }
}
