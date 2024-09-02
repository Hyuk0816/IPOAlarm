package com.sideprj.ipoAlarm.domain.alarm.repository;

import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;

import java.util.List;

public interface AlarmRepositoryCustom {

    Alarm checkIfAlarmExists(String ipoName, Long userId);

    List<Alarm> findByUserId(Long id);

    List<MyAlarmDto> fetchMyAlarms(Long userId);

}
