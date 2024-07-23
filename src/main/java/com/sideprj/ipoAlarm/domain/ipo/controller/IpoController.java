package com.sideprj.ipoAlarm.domain.ipo.controller;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.domain.ipo.service.impl.IpoServiceImpl;
import com.sideprj.ipoAlarm.domain.ipo.vo.IpoDataSaveVo;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.SignStyle;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ipo")
public class IpoController {

    private final IpoService ipoService;

    @GetMapping("/data")
    public ResponseEntity<PageResponseVo<IpoGetAllDto>> fetchIpo(@RequestParam(required = false) String ipoName,
                                                                 @RequestParam(required = false) String start,
                                                                 @RequestParam(required = false) String end,
                                                                 Pageable pageable) throws IOException, ParseException {

        Date startDate = IpoServiceImpl.convertDate(start);
        Date endDate = IpoServiceImpl.convertDate(end);

        IpoSearchRequestVo ipoSearchRequestVo = IpoSearchRequestVo.builder()
                .ipoName(ipoName)
                .searchStartDate(startDate)
                .searchEndDate(endDate)
                .build();

        PageResponseVo<IpoGetAllDto> ipoGetAllDtoPageResponseVo = ipoService.fetchIpo(ipoSearchRequestVo, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ipoGetAllDtoPageResponseVo);
    }

}
