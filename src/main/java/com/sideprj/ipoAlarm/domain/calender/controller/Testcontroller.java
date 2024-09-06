package com.sideprj.ipoAlarm.domain.calender.controller;

import com.sideprj.ipoAlarm.domain.calender.client.KakaoCalender;
import com.sideprj.ipoAlarm.domain.calender.service.KakaoCalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev/calender")
public class Testcontroller {

    private final KakaoCalenderService kakaoCalenderService;

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("Authorization: Bearer ")String accessToken ) {
        kakaoCalenderService.calenderSetting(accessToken);

        return ResponseEntity.ok("OK");
    }
}
