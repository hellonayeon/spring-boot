package com.sparta.selectshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 시간 자동 변경 'Timestamped'
@SpringBootApplication
public class SelectShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectShopApplication.class, args);
    }
}
