package com.diveinku.jasome.src.config;

import com.diveinku.jasome.src.commons.AuthenticationInterceptor;
import com.diveinku.jasome.src.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final JwtService jwtService;
    private final AuthenticationInterceptor interceptor;

    @Autowired
    public InterceptorConfig(JwtService jwtService, AuthenticationInterceptor interceptor) {
        this.jwtService = jwtService;
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(jwtService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/swagger-ui.html", "/swagger/**",
                        "/swagger-resources/**", "/v2/api-docs", "/health");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
