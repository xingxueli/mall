package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.Intercepter.HeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HeaderInterceptor())
                .addPathPatterns("/room/**","/order/**");

    }
}
