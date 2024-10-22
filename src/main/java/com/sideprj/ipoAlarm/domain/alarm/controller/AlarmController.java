package com.sideprj.ipoAlarm.domain.alarm.controller;

import com.sideprj.ipoAlarm.domain.alarm.constants.AlarmConstants;
import com.sideprj.ipoAlarm.domain.alarm.service.AlarmService;
import com.sideprj.ipoAlarm.domain.alarm.vo.AlarmResponseVo;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(
            summary = "Save Alarm Rest API",
            description = "Save Subscription Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP STATUS Created"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP STATUS UNAUTHORIZED"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS NOT FOUND"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
    }
    )
    @PostMapping( value = "/data")
    public ResponseEntity<AlarmResponseVo> saveAlarm(@RequestParam String ipoName,
                                                     @UserInfo User user) {
        alarmService.save(ipoName,user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AlarmResponseVo(AlarmConstants.status_201,AlarmConstants.msg_201 ));
    }

    @GetMapping(value = "/count")
    public Long countAlarm(@UserInfo User user) {
        return alarmService.countMyAlarm(user);
    }
}
