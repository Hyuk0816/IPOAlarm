package com.sideprj.ipoAlarm.util.redis.serializer;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

    private final ObjectMapper objectMapper;

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        // 타입 정보를 명시적으로 제공하여 역직렬화
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(PageResponseVo.class, ListingSharesGetAllDto.class); // YourDtoClass를 실제 DTO로 변경
        try {
            return objectMapper.readValue(bytes, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
