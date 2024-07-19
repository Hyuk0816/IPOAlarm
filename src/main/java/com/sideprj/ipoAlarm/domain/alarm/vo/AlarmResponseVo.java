package com.sideprj.ipoAlarm.domain.alarm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AlarmResponseVo {

    private String statusCode;

    private String statusMsg;
}
