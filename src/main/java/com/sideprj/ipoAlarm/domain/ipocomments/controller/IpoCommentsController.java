package com.sideprj.ipoAlarm.domain.ipocomments.controller;

import com.amazonaws.Response;
import com.sideprj.ipoAlarm.domain.ipocomments.constants.IpoCommentConstants;
import com.sideprj.ipoAlarm.domain.ipocomments.service.IpoCommentsService;
import com.sideprj.ipoAlarm.domain.ipocomments.vo.request.IpoCommentsRequest;
import com.sideprj.ipoAlarm.domain.ipocomments.vo.response.IpoCommentsResponse;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ipo_comments")
public class IpoCommentsController {

    private final IpoCommentsService ipoCommentsService;

    @Operation(
            summary = "Comments in IPO Detail Page Rest API",
            description = "Create Comments in IPO Detail Page Rest API"
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
    @PostMapping("/comment/{ipoName}")
    public ResponseEntity<IpoCommentsResponse> comments(@PathVariable String ipoName, @RequestBody IpoCommentsRequest commentsRequest) {
        ipoCommentsService.comments(ipoName, commentsRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new IpoCommentsResponse(IpoCommentConstants.STATUS_201, IpoCommentConstants.MESSAGE_201));
    }
}
