package com.sideprj.ipoAlarm.domain.mypage.service.impl;

import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepository;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.ipo.repository.impl.IpoRepositoryImpl;
import com.sideprj.ipoAlarm.domain.mypage.MyAlarmDto;
import com.sideprj.ipoAlarm.domain.mypage.MyPageDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final IpoRepository ipRepository;



    @Override
    public MyPageDto userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        return MyPageDto.builder()
                .email(user.getEmail())
                .image(user.getImage())
                .build();
    }

    @Override
    public List<MyAlarmDto> myAlarm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));
        List<Alarm> myAlarmList = alarmRepository.findByUserId(user.getUserId());
        return ipRepository.fetchMyAlarm(myAlarmList);
    }
}
