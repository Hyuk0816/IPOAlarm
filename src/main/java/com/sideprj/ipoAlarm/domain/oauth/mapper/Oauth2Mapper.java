package com.sideprj.ipoAlarm.domain.oauth.mapper;

import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;

public class Oauth2Mapper {

    public static LoginDto mapFromKakaoResourceDtoToLoginDto(KakaoResourceDto kakaoResourceDto) {
        return LoginDto.builder()
                .email(kakaoResourceDto.getEmail())
                .password(kakaoResourceDto.getId())
                .build();
    }
}
