package com.sideprj.ipoAlarm.domain.user.service;

import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import com.sideprj.ipoAlarm.domain.user.vo.response.AccessTokenResponse;
import com.sideprj.ipoAlarm.domain.user.vo.response.UserInfoKakaoTokenVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;


public interface AuthService {
    void login(LoginDto loginDto, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    AccessTokenResponse getAccessToken(HttpServletRequest request, HttpServletResponse response);

    void checkAuthentication(Authentication authentication);

    UserInfoKakaoTokenVo getUserNameAndKakaoAccessToken(String token);
}
