package com.sideprj.ipoAlarm.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDto {

    private String nickName;
    private String image;
    private List<MyAlarmDto> myAlarm;
    private List<MyListingSharesAlarmsDto> myListingShares;

}
