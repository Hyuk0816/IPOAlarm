package com.sideprj.ipoAlarm.domain.user.service;

import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import com.sideprj.ipoAlarm.domain.user.vo.response.AccessTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;


public interface AuthService {
    void login(LoginDto loginDto, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    AccessTokenResponse getAccessToken(HttpServletRequest request, HttpServletResponse response);

}
