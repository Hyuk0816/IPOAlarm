package com.sideprj.ipoAlarm.domain.listingalarm.repository;

import com.sideprj.ipoAlarm.domain.listingalarm.entity.ListingSharesAlarms;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;

import java.util.List;

public interface ListingSharesAlarmsRepositoryCustom {

    List<MyListingSharesAlarmsDto> fetchMyListingSharesAlarms(Long userID);

    ListingSharesAlarms checkAlreadyExists(String listingShares, Long userID);

    Long countMyListingSharesAlarms(Long userID);
}
