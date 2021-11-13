package com.sparta.springcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // configuration class
@EnableWebSecurity // enable 'spring security'
@EnableGlobalMethodSecurity(securedEnabled = true) // enable 'Authorization`, @Secured annotation
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/images/**", "/css/**", "/secret/**").permitAll() // 특정 리소스 요청을 '권한 없이' 허용 (URL 허용 정책 변경)
                .antMatchers("/user/**").permitAll() // 특정 API 요청을 '권한 없이' 허용
                .anyRequest().authenticated() // 모든 요청은 '인증 완료' 후에 허용
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login") // 로그인 처리 경로: <form> 'action' 속성과 일치
                .failureUrl("/user/login/error")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden"); // '인가' 실패
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
