package com.sideprj.ipoAlarm.domain.listingshares.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MostValuableListingShare {

    private String ipoName;

    private Double changeRateOpeningToOfferingPrice;

}