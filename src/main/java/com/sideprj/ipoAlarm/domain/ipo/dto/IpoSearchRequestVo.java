package com.sideprj.ipoAlarm.domain.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpoSearchRequestVo {

    private String ipoName;

    @Schema(description = "조회 기준 시작", example = "2024-07-01")
    private Date searchStartDate;

    @Schema(description = "조회 기준 끝", example = "2024-07-30")
    private Date searchEndDate;
}
