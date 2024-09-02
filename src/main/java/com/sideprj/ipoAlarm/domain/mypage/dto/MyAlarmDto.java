package com.sideprj.ipoAlarm.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyAlarmDto {

    private String ipoName;
    private String ipoPrice;
    private String confirmPrice;
    private String securities;
    private Date startDate;
}
