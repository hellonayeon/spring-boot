package com.sparta.itembasket.aop;

import com.sparta.itembasket.domain.User;
import com.sparta.itembasket.domain.UserTime;
import com.sparta.itembasket.repository.UserTimeRepository;
import com.sparta.itembasket.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component // 스프링 IoC 에 빈으로 등록
@Aspect
public class UserTimeAop {
    private final UserTimeRepository userTimeRepository;

    @Around("execution(public * com.sparta.itembasket.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
            // 측정 종료 시간
            long endTime = System.currentTimeMillis();
            // 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;
            // 로그인 회원이 없는 경우, 수행시간 기록하지 않음
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                // 로그인 회원 -> loginUser 변수
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal(); // 로그인 사용자의 세션을 가져오기 위해 사용
                User loginUser = userDetails.getUser();

                // 수행시간 및 DB 에 기록
                UserTime userTime = userTimeRepository.findByUser(loginUser);
                if (userTime != null) {
                    // 로그인 회원의 기록이 있으면
                    long totalTime = userTime.getTotalTime();
                    totalTime = totalTime + runTime;
                    userTime.updateTotalTime(totalTime);
                } else {
                    // 로그인 회원의 기록이 없으면
                    userTime = new UserTime(loginUser, runTime);
                }

                System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + " ms");
                userTimeRepository.save(userTime);
            }
        }
    }
}
