package com.sparta.springcore.controller;

import com.sparta.springcore.domain.User;
import com.sparta.springcore.domain.UserTime;
import com.sparta.springcore.dto.ItemDto;
import com.sparta.springcore.repository.UserTimeRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.util.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchRequestController {
    private final NaverShopSearch naverShopSearch;
    private final UserTimeRepository userTimeRepository;

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        long startTime = System.currentTimeMillis();

        try {
            String resultString = naverShopSearch.search(query);
            return naverShopSearch.fromJSONtoItems(resultString);
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;

            User user = userDetails.getUser();
            UserTime userTime = userTimeRepository.findByUser(user);
            if(userTime != null) {
                long totalTime = userTime.getTotalTime();
                totalTime += runTime;
                userTime.updateTotalTime(totalTime);
            } else {
                userTime = new UserTime(user, runTime);
            }

            System.out.println("[User Time] User: " + userTime.getUser().getUsername() + ", Total Time: " + userTime.getTotalTime() + " ms");
            userTimeRepository.save(userTime);
        }
    }
}
