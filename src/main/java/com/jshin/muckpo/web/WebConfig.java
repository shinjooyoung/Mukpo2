package com.jshin.muckpo.web;

import com.jshin.muckpo.web.interceptor.LoginAuthInterceptor;
import com.jshin.muckpo.web.jwt.JwtTokenProvider;
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
                .addPathPatterns("/*")
                .excludePathPatterns("/user");
    }

    @Bean
    public JwtTokenProvider getJwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public PublicApi getScrapApi() {
        return new PublicApi();
    }
}
