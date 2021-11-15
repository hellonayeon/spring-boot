package com.sparta.springcore.aop;

import com.sparta.springcore.domain.User;
import com.sparta.springcore.domain.UserTime;
import com.sparta.springcore.repository.UserTimeRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
public class UserTimeAop {
    private final UserTimeRepository userTimeRepository;

    @Around("execution(public * com.sparta.springcore.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try{
            // 핵심 기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();

                UserTime userTime = userTimeRepository.findByUser(loginUser);
                if (userTime != null) {
                    long totalTime = userTime.getTotalTime();
                    totalTime += runTime;
                    userTime.updateTotalTime(totalTime);
                } else {
                   userTime = new UserTime(loginUser, runTime);
                }

                System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + " ms");
                userTimeRepository.save(userTime);
            }
        }
    }
}
