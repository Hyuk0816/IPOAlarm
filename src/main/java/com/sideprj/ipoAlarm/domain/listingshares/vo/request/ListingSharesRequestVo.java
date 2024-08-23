package com.sideprj.ipoAlarm.domain.listingshares.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ListingSharesRequestVo {

    @Schema(description = "상장 예정 주식 이름", example = "아이스크림미디어")
    private String ipoName;

    @Schema(description = "상장일 검색 시작 값 ", example = "2024-08-01", type = "String", format = "date-time")
    private String listingStartDate;

    @Schema(description = "상장일 검색 끝 값 ", example = "2024-08-01", type = "String", format = "date-time")
    private String listingEndDate;

    @Schema(description = "전일 대비 가격 변동율 검색 시작 값", example = "-1.8")
    private Double minChangeRatePrevious;

    @Schema(description = "전일 대비 가격 변동율 검색 끝 값", example = "-1.8")
    private Double maxChangeRatePrevious;

    @Schema(description = "공모가격 대비 가격 변동율 검색 시작 값", example = "100")
    private Double minChangeRateOfferingPrice;

    @Schema(description = "공모가격 대비 가격 변동율 검색 끝 값", example = "100")
    private Double maxChangeRateOfferingPrice;

    @Schema(description = "공모가격 대비 상장일 당일 가격 변동율 검색 시작 값", example = "200")
    private Double minChangeRateOpeningToOfferingPrice;

    @Schema(description = "공모가격 대비 상장일 당일 가격 변동율 검색 끝 값", example = "200")
    private Double maxChangeRateOpeningToOfferingPrice;

}
