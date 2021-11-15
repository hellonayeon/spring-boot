package com.sparta.springcore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTimeDto {
    private String username;
    private long totalTime;
}
