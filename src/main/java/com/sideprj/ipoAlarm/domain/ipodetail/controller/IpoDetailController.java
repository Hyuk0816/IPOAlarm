package com.sideprj.ipoAlarm.domain.ipodetail.controller;

import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;
import com.sideprj.ipoAlarm.domain.ipodetail.service.IpoDetailService;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ipo_detail")
public class IpoDetailController {

    private final IpoDetailService ipoDetailService;


    @Operation(
            summary = "IPO Detail Data Search Rest API",
            description = "Create IPO data Detail get Request"
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
    @GetMapping("/data/{ipoName}")
    public ResponseEntity<IpoDetailFetchDto> getIpoDetail(@PathVariable String ipoName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ipoDetailService.getIpoDetail(ipoName));

    }
}
