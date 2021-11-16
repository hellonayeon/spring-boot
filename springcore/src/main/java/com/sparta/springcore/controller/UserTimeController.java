package com.sparta.springcore.controller;

import com.sparta.springcore.domain.UserTime;
import com.sparta.springcore.domain.UserTimeDto;
import com.sparta.springcore.repository.UserTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserTimeController {
    private final UserTimeRepository userTimeRepository;

    @Secured("ROLE_ADMIN") // '인가' 필요
    @GetMapping("/user/time")
    public List<UserTimeDto> getUserTime() {
        List<UserTime> allUserTime = userTimeRepository.findAll();

        List<UserTimeDto> allUserTimeDto = new ArrayList<>();
        for(UserTime userTime : allUserTime) {
            String username = userTime.getUser().getUsername();
            long totalTime = userTime.getTotalTime();
            UserTimeDto dto = new UserTimeDto(username, totalTime);
            allUserTimeDto.add(dto);
        }

        return allUserTimeDto;
    }
}
