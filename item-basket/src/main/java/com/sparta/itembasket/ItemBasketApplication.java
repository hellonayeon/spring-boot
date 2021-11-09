package com.sparta.itembasket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ItemBasketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemBasketApplication.class, args);
    }

}
