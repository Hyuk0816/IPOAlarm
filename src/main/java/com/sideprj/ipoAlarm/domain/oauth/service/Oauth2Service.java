package com.sideprj.ipoAlarm.domain.oauth.service;

import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.util.Map;

public interface Oauth2Service {

     KakaoResourceDto getUserInfo(String accessToken, String registration);

     Map<String ,String> getAccessToken(String authorizationCode, String registration);

     void socialSignIn(String email, String password, String image,HttpServletResponse response) throws IOException;
     void socialLogin(String code, String registration, HttpServletResponse response ) throws IOException;
}
