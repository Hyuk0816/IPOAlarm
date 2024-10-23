package com.sideprj.ipoAlarm.domain.mypage.service;

import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyPageDto;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;

import java.util.List;

public interface MyPageService {

    MyPageDto userInfo(User user);

    List<MyAlarmDto> fetchMyAlarmList(User user);

    List<MyListingSharesAlarmsDto> fetchMyListingSharesList(User user);
}
