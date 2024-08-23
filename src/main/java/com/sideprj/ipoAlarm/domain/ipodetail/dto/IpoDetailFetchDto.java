package com.sideprj.ipoAlarm.domain.ipodetail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IpoDetailFetchDto {

    private String ipoName;
    private String industry;
    private String representative;
    private String revenue;
    private String netProfit;
    private String totalOfferedShares;

}
