package com.sideprj.ipoAlarm.domain.mypage.service.impl;

import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepository;
import com.sideprj.ipoAlarm.domain.listingalarm.repository.ListingSharesAlarmsRepository;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyPageDto;
import com.sideprj.ipoAlarm.domain.mypage.service.MyPageService;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    private final AlarmRepository alarmRepository;
    private final ListingSharesAlarmsRepository listingSharesAlarmsRepository;


    @Override
    public MyPageDto userInfo(@UserInfo User user) {

        List<MyAlarmDto> myAlarmList = fetchMyAlarmList(user);
        List<MyListingSharesAlarmsDto> myListingSharesAlarmList = fetchMyListingSharesList(user);

        return MyPageDto.builder()
                .nickName(user.getNickName())
                .image(user.getImage())
                .myAlarm(myAlarmList)
                .myListingShares(myListingSharesAlarmList)
                .build();
    }

    @Override
    public List<MyAlarmDto> fetchMyAlarmList(@UserInfo User user) {
        return alarmRepository.fetchMyAlarms(user.getUserId());
    }

    @Override
    public List<MyListingSharesAlarmsDto> fetchMyListingSharesList(@UserInfo User user) {
        return listingSharesAlarmsRepository.fetchMyListingSharesAlarms(user.getUserId());
    }
}
