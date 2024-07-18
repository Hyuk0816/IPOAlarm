package com.sideprj.ipoAlarm.util.S3.controller;

import com.sideprj.ipoAlarm.util.S3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/data")
    public ResponseEntity<String > downCsv() throws FileNotFoundException {
        s3Service.downloadFile();
        return ResponseEntity
                .ok()
                .body("다운로드가 정상적으로 되었습니다");
    }
 }
