package com.sideprj.ipoAlarm.domain.oauth.controller;

import com.sideprj.ipoAlarm.domain.oauth.service.Oauth2Service;
import com.sideprj.ipoAlarm.domain.user.constants.AuthConstants;
import com.sideprj.ipoAlarm.domain.user.vo.UserStatusResponseVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final Oauth2Service oauth2Service;

    @GetMapping("/dev/login/oauth/{registration}")
    public ResponseEntity<UserStatusResponseVo> socialLogin(@RequestParam String code,
                                                            @PathVariable String registration,
                                                            HttpServletResponse response) throws BadRequestException {
        oauth2Service.socialLogin(code, registration, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserStatusResponseVo(AuthConstants.STATUS_200, AuthConstants.MESSAGE_Login_200));
    }

}
