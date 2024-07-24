package com.sideprj.ipoAlarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class IpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpoApplication.class, args);
    }

}
