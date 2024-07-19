package com.sideprj.ipoAlarm.domain.alarm.repository;

import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;

public interface AlarmRepositoryCustom {

    Alarm checkIfAlarmExists(String ipoName, Long userId);

}
