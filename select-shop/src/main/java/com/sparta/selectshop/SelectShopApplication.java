package com.sparta.selectshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케쥴러 작동 'Scheduler'
@EnableJpaAuditing // 시간 자동 변경 'Timestamped'
@SpringBootApplication
public class SelectShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectShopApplication.class, args);
    }
}
