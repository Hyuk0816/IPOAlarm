package com.sideprj.ipoAlarm.domain.message.controller;

import com.sideprj.ipoAlarm.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev/message")
public class testController {

    private final MessageService messageService;


    @PostMapping("/test")
    public ResponseEntity<String> test() {
        messageService.messageSetting();

        return ResponseEntity.ok("OK");
    }
}
