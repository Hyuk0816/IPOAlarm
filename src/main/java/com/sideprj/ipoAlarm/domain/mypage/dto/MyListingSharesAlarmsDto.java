package com.sideprj.ipoAlarm.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyListingSharesAlarmsDto {

    private String listingShares;
    private String listingDate;
    private String offeringPrice;

}
