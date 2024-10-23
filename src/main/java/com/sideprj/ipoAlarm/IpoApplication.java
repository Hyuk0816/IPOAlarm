package com.sideprj.ipoAlarm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@OpenAPIDefinition(servers = {@Server(url = "https://ipo-alarm.com"),
                              @Server(url = "http://localhost:8080")})
public class IpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpoApplication.class, args);
    }

}
