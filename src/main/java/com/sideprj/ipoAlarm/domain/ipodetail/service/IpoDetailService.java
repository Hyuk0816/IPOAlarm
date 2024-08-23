package com.sideprj.ipoAlarm.domain.ipodetail.service;

import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;

public interface IpoDetailService {

    IpoDetailFetchDto getIpoDetail(String  ipoName);
}
