package com.sideprj.ipoAlarm.domain.oauth.controller;

import com.sideprj.ipoAlarm.domain.oauth.service.Oauth2Service;
import com.sideprj.ipoAlarm.domain.user.constants.AuthConstants;
import com.sideprj.ipoAlarm.domain.user.vo.UserStatusResponseVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final Oauth2Service oauth2Service;

    @GetMapping("/dev/login/oauth/{registration}")
    public void socialLogin(@RequestParam String code,
                            @PathVariable String registration,
                            HttpServletResponse response) throws IOException {
        oauth2Service.socialLogin(code, registration, response);

    }

}
