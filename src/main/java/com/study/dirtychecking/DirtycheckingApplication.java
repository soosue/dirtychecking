package com.study.dirtychecking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DirtycheckingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DirtycheckingApplication.class, args);
    }

}
