package com.shop.fruitfruit;

import com.shop.fruitfruit.web.interceptor.AdminLoginInterceptor;
import com.shop.fruitfruit.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 유지
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/user/join", "/*/js/**", "/*/img/**", "/*/css/**", "/*/*.ico", "/error", "/admin", "/admin/**");

        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin", "/admin/login", "/*/js/**", "/*/img/**", "/*/css/**", "/*/*.ico", "/error");
    }
}
