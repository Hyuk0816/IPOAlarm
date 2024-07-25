package com.sideprj.ipoAlarm.domain.oauth.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoUserInfoDto;
import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
public class Oauth2Mapper {

    public static LoginDto mapFromKakaoResourceDtoToLoginDto(KakaoUserInfoDto kakaoUserInfoDto) {
        return LoginDto.builder()
                .email(kakaoUserInfoDto.getEmail())
                .password(kakaoUserInfoDto.getId())
                .build();
    }

    public static KakaoUserInfoDto mapFromKakaoResourceDtoToKakaoUserInfoDto(KakaoResourceDto kakaoResourceDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // KakaoResourceDto를 Map으로 변환
            Map<String, Object> firstMap = objectMapper.convertValue(kakaoResourceDto, new TypeReference<>() {});

            // kakao_account을 Map으로 변환
            Map<String, Object> kakaoAccountMap = (Map<String, Object>) firstMap.get("kakao_account");

            // profile을 Map으로 변환
            Map<String, Object> profileMap = (Map<String, Object>) kakaoAccountMap.get("profile");


            return KakaoUserInfoDto.builder()
                    .id(kakaoResourceDto.getId())
                    .email(kakaoAccountMap.get("email").toString())
                    .image(profileMap.get("thumbnail_image_url").toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
