package com.sideprj.ipoAlarm.config.aws;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
public class OpenFeignConfig {

    private final RedisTemplate<String ,String> redisTemplate;


    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            requestTemplate.header("Authorization", redisTemplate.opsForValue().get("rlawogur816@naver.com-kakao_access"));
            log.info(redisTemplate.opsForValue().get("rlawogur816@naver.com-kakao_access") + " 로그할때도 호출?? ");
            requestTemplate.header("Content-Type", "application/x-www-form-urlencoded");
        };
    }

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}
