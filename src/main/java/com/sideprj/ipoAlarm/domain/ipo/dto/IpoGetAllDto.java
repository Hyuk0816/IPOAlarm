package com.sideprj.ipoAlarm.domain.ipo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IpoGetAllDto {

    @Schema(description = "공모주명", example = "두산 로보틱스")
    private String ipoName;

    @Schema(description = "청약 가격", example = "24000 ~ 26000")
    private String ipoPrice;

    @Schema(description = "확정 가격", example = "26000")
    private String confirmPrice;

    @Schema(description = "경쟁률", example = "750:1")
    private String competitionRate;

    @Schema(description = "주간사", example = "삼성증권,한국투자,KB증권")
    private String securities;

    @Schema(description = "청약 시작일", example = "2024.07.17")
    private Date startDate;

    @Schema(description = "청약 마감일", example = "2024.07.19")
    private Date endDate;
}
