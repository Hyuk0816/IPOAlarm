package com.sideprj.ipoAlarm.domain.listingshares.controller;

import com.sideprj.ipoAlarm.domain.ipo.service.impl.IpoServiceImpl;
import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.service.ListingSharesService;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
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

import java.text.ParseException;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/listing_shares")
public class ListingSharesController {

    private final ListingSharesService listingSharesService;
    @Operation(
            summary = "listing Data Search Rest API",
            description = "Create listing data get Request"
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
    public ResponseEntity<PageResponseVo<ListingSharesGetAllDto>> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listingSharesService.fetchListingShares(requestVo,pageable));

    }
//    @GetMapping("/data")
//    public ResponseEntity<PageResponseVo<ListingSharesGetAllDto>> fetchListingShares(@RequestParam(required = false)String ipoName,
//                                                                                     @RequestParam(required = false)String startDate,
//                                                                                     @RequestParam(required = false)String endDate,
//                                                                                     @RequestParam(required = false)Double minChangeRatePrevious,
//                                                                                     @RequestParam(required = false)Double maxChangeRatePrevious,
//                                                                                     @RequestParam(required = false)Double minChangeRateOfferingPrice,
//                                                                                     @RequestParam(required = false)Double maxChangeRateOfferingPrice,
//                                                                                     @RequestParam(required = false)Double minChangeRateOpeningToOfferingPrice,
//                                                                                     @RequestParam(required = false)Double maxChangeRateOpeningToOfferingPrice,
//                                                                                     Pageable pageable
//                                                                                     ) throws ParseException {
//
//
//        ListingSharesRequestVo requestVo = ListingSharesRequestVo.builder()
//                .ipoName(ipoName)
//                .listingStartDate(listingStartDate)
//                .listingendDate(listingEndDate)
//                .minChangeRatePrevious(minChangeRatePrevious)
//                .maxChangeRatePrevious(maxChangeRatePrevious)
//                .minChangeRateOfferingPrice(minChangeRateOfferingPrice)
//                .maxChangeRateOfferingPrice(maxChangeRateOfferingPrice)
//                .minChangeRateOpeningToOfferingPrice(minChangeRateOpeningToOfferingPrice)
//                .maxChangeRateOpeningToOfferingPrice(maxChangeRateOpeningToOfferingPrice)
//                .build();
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(listingSharesService.fetchListingShares(requestVo,pageable));
//
//    }

}
