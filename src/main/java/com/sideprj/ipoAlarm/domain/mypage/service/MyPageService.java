package com.sideprj.ipoAlarm.domain.mypage.service;

import com.sideprj.ipoAlarm.domain.mypage.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.MyPageDto;

import java.util.List;

public interface MyPageService {

    MyPageDto userInfo();

    List<MyAlarmDto> fetchMyAlarmList();
}
