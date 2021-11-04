package com.sparta.demo.controller;

import com.sparta.demo.domain.Lecture;
import com.sparta.demo.domain.LectureRepository;
import com.sparta.demo.dto.LectureRequestDto;
import com.sparta.demo.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LectureController {

    private final LectureRepository lectureRepository;
    private final LectureService lectureService;

    @GetMapping("/api/lectures")
    public List<Lecture> getLectures() {
        return lectureRepository.findAll();
    }

    @PostMapping("/api/lectures")
    public Lecture createLecture(@RequestBody LectureRequestDto requestDto) {
        Lecture lecture = new Lecture(requestDto);
        return lectureRepository.save(lecture);
    }

    @PutMapping("/api/lectures/{id}")
    public Long updateLecture(@PathVariable Long id, @RequestBody LectureRequestDto requestDto) {
        return lectureService.update(id, requestDto);
    }

    @DeleteMapping("/api/lectures/{id}")
    public Long deleteLecture(@PathVariable Long id) {
        lectureRepository.deleteById(id);
        return id;
    }
}
