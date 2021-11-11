package com.sparta.itembasket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTimeDto {
    String username;
    long totalTime;
}