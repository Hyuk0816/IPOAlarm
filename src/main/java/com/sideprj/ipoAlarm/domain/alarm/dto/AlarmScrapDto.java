package com.sideprj.ipoAlarm.domain.alarm.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmScrapDto {

    private Long alarmId;

    private String ipoName;

    private Date startDate;

    private Long userId;

}
