package com.sideprj.ipoAlarm.util.redis.config;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisRepositoryConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String password;



    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
       RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
       config.setHostName(host);
       config.setPort(port);
       config.setPassword(password);
       return new LettuceConnectionFactory(config);

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }


    @Bean
    public  RedisTemplate<String, Long> redisTemplateLong() {
        RedisTemplate<String,Long >  redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // 키와 값을 직렬화하는 방법 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }




    @Bean
    public RedisTemplate<String, PageResponseVo<IpoGetAllDto>> redisTemplateIpoGetAllDto() {
        RedisTemplate<String,PageResponseVo<IpoGetAllDto>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // timestamp 형식 안 따르도록 설정
//        mapper.registerModule(new JavaTimeModule()); // LocalDateTime 매핑을 위해 모듈 활성화
//        mapper.registerModule(new Jdk8Module()); // JDK 8 모듈 활성화
//        mapper.registerModule(new ParameterNamesModule()); // 생성자의 매개변수 이름을 사용하여 JSON 속성과 매핑
//        return mapper;
//    }

    @Bean
    public CacheManager cacheManager() {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        Map<String, RedisCacheConfiguration> cacheConfigurations = getStringRedisCacheConfigurationMap(redisCacheConfiguration);

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    private static Map<String, RedisCacheConfiguration> getStringRedisCacheConfigurationMap(RedisCacheConfiguration redisCacheConfiguration) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("fetchAllIpoData", redisCacheConfiguration.entryTtl(Duration.ofHours(10)));
//        cacheConfigurations.put("getIpoDetail", redisCacheConfiguration.entryTtl(Duration.ofMinutes(15)));
        cacheConfigurations.put("monthlyProfit", redisCacheConfiguration.entryTtl(Duration.ofHours(10)));
        return cacheConfigurations;
    }

    //redis의 key generator
    @Bean
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder key = new StringBuilder();
            key.append(target.getClass().getSimpleName()).append(".");
            key.append(method.getName());
            for (Object param : params) {
                key.append(".").append(param);
            }
            return key.toString();
        };
    }


}
