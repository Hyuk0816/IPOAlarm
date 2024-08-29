package com.sideprj.ipoAlarm.domain.ipodetail.service;

import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;
import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchWithComments;

public interface IpoDetailService {

    IpoDetailFetchWithComments getIpoDetail(String  ipoName);
}
