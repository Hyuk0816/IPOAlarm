package com.sideprj.ipoAlarm.domain.alarm.service;

import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;
import org.springframework.security.core.Authentication;

public interface AlarmService {

    void save(String ipoName, User user);

    Long countMyAlarm(User user);

}
