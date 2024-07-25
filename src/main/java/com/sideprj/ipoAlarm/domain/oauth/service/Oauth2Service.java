package com.sideprj.ipoAlarm.domain.oauth.service;

import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;

import java.util.Map;

public interface Oauth2Service {

    public KakaoResourceDto getUserInfo(String accessToken, String registration);

    public Map<String ,String> getAccessToken(String authorizationCode, String registration);

    public void socialSignIn(String email, String password, String image);

    public void socialLogin(String code, String registration, HttpServletResponse response ) throws BadRequestException;

}
