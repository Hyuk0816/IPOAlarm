package com.sideprj.ipoAlarm.domain.listingshares.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OfferingToOpeningPriceMonthlyProfitDto {

    @Schema(description = "공모가격 대비 상장 게시 날 월 평균 이익")
    Double monthlyProfit;
}
