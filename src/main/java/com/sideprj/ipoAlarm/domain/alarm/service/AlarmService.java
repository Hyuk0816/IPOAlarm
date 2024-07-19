package com.sideprj.ipoAlarm.domain.alarm.service;

import org.springframework.security.core.Authentication;

public interface AlarmService {

    void save(String ipoName, Authentication authentication);

}
