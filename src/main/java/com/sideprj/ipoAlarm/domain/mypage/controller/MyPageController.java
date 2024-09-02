package com.sideprj.ipoAlarm.domain.mypage.controller;

import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyPageDto;
import com.sideprj.ipoAlarm.domain.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    @Operation(
            summary = "get userInfo Rest API",
            description = "Save Subscription Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
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
    @GetMapping("/data")
    public ResponseEntity<MyPageDto> getUserInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myPageService.userInfo());
    }

    @Operation(
            summary = "fetch my Alarm List Rest API",
            description = "Save Subscription Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
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
    @GetMapping("/myalarm")
    public ResponseEntity<List<MyAlarmDto>> getMyAlarm() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myPageService.fetchMyAlarmList());
    }
    @Operation(
            summary = "fetch my Listing Alarm List Rest API",
            description = "Get my Listing Alarm List"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
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
    @GetMapping("/myListingAlarm")
    public ResponseEntity<List<MyListingSharesAlarmsDto>> getMyListingAlarm(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myPageService.fetchMyListingSharesList());
    }
}
