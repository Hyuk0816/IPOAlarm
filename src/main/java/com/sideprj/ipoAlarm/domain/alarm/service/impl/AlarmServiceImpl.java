package com.sideprj.ipoAlarm.domain.alarm.service.impl;

import com.sideprj.ipoAlarm.domain.alarm.constants.AlarmConstants;
import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepository;
import com.sideprj.ipoAlarm.domain.alarm.service.AlarmService;
import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepository;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.util.exception.AlarmAlreadyExistsException;
import com.sideprj.ipoAlarm.util.exception.BetweenDateException;
import com.sideprj.ipoAlarm.util.exception.EndDateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final IpoRepository ipoRepository;

    @Override
    public void save(String ipoName, Authentication authentication) {

        Ipo ipo = ipoRepository.findByIpoName(ipoName);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        if(alarmRepository.checkIfAlarmExists(ipoName, user.getUserId())!=null){
            throw new AlarmAlreadyExistsException(AlarmConstants.msg_500, ipoName);
        }
        Date today = new Date();

        if(today.after(ipo.getEndDate())){
            throw new EndDateException(AlarmConstants.msg_end);
        } else if((today.after(ipo.getStartDate()) && today.before(ipo.getEndDate()))) {
            throw new BetweenDateException(AlarmConstants.msg_between);
        }

        Alarm alarm = Alarm.builder()
                .ipo(ipo)
                .user(user)
                .startDate(ipo.getStartDate())
                .build();

        alarmRepository.save(alarm);
    }
}
