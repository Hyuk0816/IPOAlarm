package com.sideprj.ipoAlarm.domain.ipo.controller;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.service.IpoService;
import com.sideprj.ipoAlarm.domain.ipo.service.impl.IpoServiceImpl;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ipo")
public class IpoController {

    private final IpoService ipoService;

    @Operation(
            summary = "IPO Data Search Rest API",
            description = "Create IPO data get Request"
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
            )

    }
    )
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
