package com.sideprj.ipoAlarm.domain.user.service;

import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;


public interface AuthService {
    void login(LoginDto loginDto, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    void getAccessToken(HttpServletRequest request, HttpServletResponse response);

}
