package com.sparta.springcore.security;

import com.sparta.springcore.controller.JwtAuthenticationEntryPoint;
import com.sparta.springcore.controller.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // configuration class
@EnableWebSecurity // enable 'spring security'
@EnableGlobalMethodSecurity(securedEnabled = true) // enable 'Authorization`, @Secured annotation
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/images/**", "/css/**", "/secret/**").permitAll() // 특정 리소스 요청을 '권한 없이' 허용 (URL 허용 정책 변경)
                .antMatchers("/user/**").permitAll() // 특정 API 요청을 '권한 없이' 허용
                // 사용자의 '인증' 없이도 접근이 가능해야하는 프론트 리소스
                .antMatchers("/basic.js").permitAll()
                // 로그인을 안하더라도 요청할 수 있는 API
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/docs/**").permitAll()
                .anyRequest().authenticated() // 모든 요청은 '인증 완료' 후에 허용
                .and()
                // 예외처리 핸들러: JwtAuthenticationEntryPoint
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // disable session (<-> enable session = sever stateful status)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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

        // HTTP 요청 필터 등록
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
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
