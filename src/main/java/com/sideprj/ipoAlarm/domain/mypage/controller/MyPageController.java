package com.sideprj.ipoAlarm.domain.mypage.controller;

import com.sideprj.ipoAlarm.domain.mypage.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.MyPageDto;
import com.sideprj.ipoAlarm.domain.mypage.service.MyPageService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/data")
    public ResponseEntity<MyPageDto> getUserInfo() {
        return ResponseEntity
                .ok()
                .body(myPageService.userInfo());
    }

    @GetMapping("/myalarm")
    public ResponseEntity<List<MyAlarmDto>> getMyAlarm() {
        return ResponseEntity
                .ok()
                .body(myPageService.myAlarm());
    }
}
