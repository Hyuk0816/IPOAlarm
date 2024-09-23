package com.sideprj.ipoAlarm.domain.listingalarm.controller;

import com.sideprj.ipoAlarm.domain.listingalarm.constants.ListingSharesAlarmsConstants;
import com.sideprj.ipoAlarm.domain.listingalarm.service.ListingSharesAlarmsService;
import com.sideprj.ipoAlarm.domain.listingalarm.vo.response.ListingSharesAlarmsSaveResponseVo;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/listing_share_alarm")
public class ListingSharesAlarmController {

    private final ListingSharesAlarmsService listingSharesAlarmsService;

    @Operation(
            summary = "listing shares Alarm Save Rest API",
            description = "Create listing shares Alarm Save Request"
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
    @PostMapping("/alarm")
    public ResponseEntity<ListingSharesAlarmsSaveResponseVo> save(@RequestParam String listingShares) {
        listingSharesAlarmsService.saveAlarm(listingShares);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ListingSharesAlarmsSaveResponseVo(ListingSharesAlarmsConstants.STATUS_201, ListingSharesAlarmsConstants.MSG_201));
    }

    @GetMapping("/count")
    public Long countMyListingSharesAlarms() {
        return listingSharesAlarmsService.countMyListingSharesAlarm();
    }

}
