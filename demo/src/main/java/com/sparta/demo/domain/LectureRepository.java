package com.sparta.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스: 기능만 정의, 실제 구현은 JpaRepository 클래스
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
