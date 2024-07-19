package com.sideprj.ipoAlarm.domain.alarm.controller;

import com.sideprj.ipoAlarm.domain.alarm.constants.AlarmConstants;
import com.sideprj.ipoAlarm.domain.alarm.service.AlarmService;
import com.sideprj.ipoAlarm.domain.alarm.vo.AlarmResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping( value = "/data")
    public ResponseEntity<AlarmResponseVo> saveAlarm(@RequestParam String ipoName, Authentication authentication) {
        alarmService.save(ipoName,authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AlarmResponseVo(AlarmConstants.status_201,AlarmConstants.msg_201 ));
    }
}
