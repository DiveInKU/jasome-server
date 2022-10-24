package com.diveinku.jasome.src.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 세션 사용 안하므로
                // swagger
                .formLogin().disable() // form 태그 만들어서 로그인을 안함
                .httpBasic().disable() // 기본 방식 안쓰고 Bearer(jwt) 방법 사용할 것
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger/**",
                        "/swagger-resources/**", "/v2/api-docs", "/health").permitAll()
                // 멤버
                .antMatchers("/members/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
        return http.build();
    }
}
