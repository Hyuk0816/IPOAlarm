package com.sideprj.ipoAlarm.domain.mypage.service.impl;

import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepository;
import com.sideprj.ipoAlarm.domain.listingalarm.repository.ListingSharesAlarmsRepository;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyPageDto;
import com.sideprj.ipoAlarm.domain.mypage.service.MyPageService;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final ListingSharesAlarmsRepository listingSharesAlarmsRepository;


    @Override
    public MyPageDto userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        List<MyAlarmDto> myAlarmDto = fetchMyAlarmList();
        List<MyListingSharesAlarmsDto> myListingSharesAlarmsDto = fetchMyListingSharesList();

        return MyPageDto.builder()
                .nickName(user.getNickName())
                .image(user.getImage())
                .myAlarm(myAlarmDto)
                .myListingShares(myListingSharesAlarmsDto)
                .build();
    }

    @Override
    public List<MyAlarmDto> fetchMyAlarmList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));
        return alarmRepository.fetchMyAlarms(user.getUserId());
    }

    @Override
    public List<MyListingSharesAlarmsDto> fetchMyListingSharesList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));
        return listingSharesAlarmsRepository.fetchMyListingSharesAlarms(user.getUserId());
    }
}
