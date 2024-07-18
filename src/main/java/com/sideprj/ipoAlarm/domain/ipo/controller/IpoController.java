package com.sideprj.ipoAlarm.domain.ipo.controller;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.domain.ipo.vo.IpoDataSaveVo;
import com.sideprj.ipoAlarm.util.csv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ipo/")
public class IpoController {

    private final IpoService ipoService;

    @PostMapping("data")
    public ResponseEntity<IpoDataSaveVo> save() throws IOException {
        ipoService.save();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new IpoDataSaveVo(IpoConstants.STATUS_201, IpoConstants.MESSAGE_201));
    }
}
