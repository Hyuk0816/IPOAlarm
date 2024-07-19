package com.sideprj.ipoAlarm.domain.alarm.mapper;

import com.sideprj.ipoAlarm.domain.alarm.dto.AlarmScrapDto;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.user.entity.User;

public class AlarmMapper {


    public static AlarmScrapDto mapToAlarmScrapDto(Ipo ipo, User user){

        return AlarmScrapDto.builder()
                .ipoName(ipo.getIpoName())
                .userId(user.getUserId())
                .startDate(ipo.getStartDate())
                .build();

    }
}
