package com.sideprj.ipoAlarm.domain.user.controller;

import com.sideprj.ipoAlarm.domain.user.constants.AuthConstants;
import com.sideprj.ipoAlarm.domain.user.dto.LoginDto;
import com.sideprj.ipoAlarm.domain.user.service.AuthService;
import com.sideprj.ipoAlarm.domain.user.vo.UserStatusResponseVo;
import com.sideprj.ipoAlarm.domain.user.vo.response.AccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;



@RequestMapping(path = "/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login Rest API",
            description = "Create Logout Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    }
    )
    @PostMapping(path = "/login")
    public void Login(@RequestBody LoginDto loginDto,
                                                       HttpServletResponse response) throws BadRequestException {
        authService.login(loginDto, response);

    }

    @Operation(
            summary = "Logout Rest API",
            description = "Create Logout Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    }
    )
    @PostMapping(path ="/logout")
    public ResponseEntity<UserStatusResponseVo> Logout(
            HttpServletResponse response,
            HttpServletRequest request) {
        authService.logout(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserStatusResponseVo(AuthConstants.STATUS_200, AuthConstants.MESSAGE_Logout_200));
    }

    @Operation(
            summary = "Fetch accessToken on header Rest API",
            description = "call accessToken with refreshToken then fetch accessToken on header." +
                    "Axios.interceptor on any frontend logic just need to call this API then possible to put accessToken on header"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    })
    @GetMapping(path ="/accessToken")
    public ResponseEntity<AccessTokenResponse> getAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        AccessTokenResponse tokensInfo = authService.getAccessToken(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokensInfo);
    }

    @GetMapping("/userInfo")
    public String getUserInfo(@RequestParam("access_token") String token) {
        return authService.getUsername(token);
    }

}

