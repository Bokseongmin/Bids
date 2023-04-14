package com.example.bids.utill.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor())
                .addPathPatterns("/mgmt/**") // 적용할 URL 패턴을 설정합니다.
                .excludePathPatterns("/login"); // 제외할 URL 패턴을 설정합니다.
    }
}
