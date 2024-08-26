package com.sideprj.ipoAlarm.domain.listingshares.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ListingSharesGetAllDto {

    @Schema(description = "기업명")
    private String ipoName;

    @Schema(description = "신규 상장일")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate listingDate;

    @Schema(description = "현재가")
    private String currentPrice;

    @Schema(description = "전일비%")
    private Double changeRatePrevious;

    @Schema(description = "공모가격")
    private String offeringPrice;

    @Schema(description = "공모가대비 등락률%")
    private Double changeRateOfferingPrice;

    @Schema(description = "시초가")
    private String openingPrice;

    @Schema(description = "시초 공모 %")
    private Double changeRateOpeningToOfferingPrice;

    @Schema(description = "첫날 종가")
    private String closingPriceFirstDay;

}
