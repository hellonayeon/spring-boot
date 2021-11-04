package com.sparta.demo.service;

import com.sparta.demo.domain.Lecture;
import com.sparta.demo.domain.LectureRepository;
import com.sparta.demo.dto.LectureRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LectureService {
    // final: 무조건 필요한 변수
    private final LectureRepository lectureRepository;

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Long update(Long id, LectureRequestDto lectureRequest) {
        Lecture lecture1 = lectureRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        lecture1.update(lectureRequest);
        // lectureRepository.save(lecture1) // 일반적으로 @Transactional + save() 명시
        return lecture1.getId();
    }
}
