package com.sparta.itembasket.controller;

import com.sparta.itembasket.domain.UserTime;
import com.sparta.itembasket.dto.UserTimeDto;
import com.sparta.itembasket.repository.UserTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserTimeController {
    private final UserTimeRepository userTimeRepository;

    // (관리자용) 회원별 API 수행 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/user/time")
    public List<UserTimeDto> getUserTime() {
        List<UserTime> allUserTime = userTimeRepository.findAll();

        // UserTime -> UserTimeDto 로 변환
        List<UserTimeDto> allUserTimeDto = new ArrayList<>();
        for (UserTime userTime : allUserTime) {
            String username = userTime.getUser().getUsername();
            long totalTime = userTime.getTotalTime();
            UserTimeDto dto = new UserTimeDto(username, totalTime);
            allUserTimeDto.add(dto);
        }

        return allUserTimeDto;
    }
}