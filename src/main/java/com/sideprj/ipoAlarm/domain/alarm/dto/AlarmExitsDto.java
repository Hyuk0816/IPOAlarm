package com.sideprj.ipoAlarm.domain.alarm.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmExitsDto {

    private String ipoName;
    private Long userId;

}
