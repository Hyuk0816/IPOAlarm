package com.sideprj.ipoAlarm.domain.listingalarm.service;

import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;

public interface ListingSharesAlarmsService {

    void saveAlarm(String listingShares, @UserInfo User user);

    Long countMyListingSharesAlarm(@UserInfo User user);


}
