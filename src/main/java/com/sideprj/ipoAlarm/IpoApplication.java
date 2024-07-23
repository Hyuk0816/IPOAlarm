package com.sideprj.ipoAlarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpoApplication.class, args);
    }

}
