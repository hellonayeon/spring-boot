package com.sparta.demo;

import com.sparta.demo.domain.Lecture;
import com.sparta.demo.domain.LectureRepository;
import com.sparta.demo.dto.LectureRequestDto;
import com.sparta.demo.service.LectureService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(LectureRepository lectureRepository, LectureService lectureService) {
        return (args) -> {
            lectureRepository.save(new Lecture("프론트엔드의 꽃, 리액트", "임민영"));
        };
    }
}
