package com.sparta.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LectureRequestDto {
    private String title;
    private String tutor;

    public LectureRequestDto(String title, String tutor) {
        this.title = title;
        this.tutor = tutor;
    }
}