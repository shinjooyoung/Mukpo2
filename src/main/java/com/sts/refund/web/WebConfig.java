package com.sts.refund.web;

import com.sts.refund.web.interceptor.LoginAuthInterceptor;
import com.sts.refund.web.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginAuthInterceptor(getJwtTokenProvider()))
                .order(1)
                .addPathPatterns("/szs/*")
                .excludePathPatterns("/szs/signup", "/szs/login");
    }

    @Bean
    public JwtTokenProvider getJwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public ScrapApi getScrapApi() {
        return new ScrapApi();
    }
}
