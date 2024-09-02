package com.sideprj.ipoAlarm.domain.mypage.service;

import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyPageDto;

import java.util.List;

public interface MyPageService {

    MyPageDto userInfo();

    List<MyAlarmDto> fetchMyAlarmList();

    List<MyListingSharesAlarmsDto> fetchMyListingSharesList();
}
