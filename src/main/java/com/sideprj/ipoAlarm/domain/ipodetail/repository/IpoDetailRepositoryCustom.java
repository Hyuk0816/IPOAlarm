package com.sideprj.ipoAlarm.domain.ipodetail.repository;

import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;

public interface IpoDetailRepositoryCustom {

    IpoDetailFetchDto fetchIpoDetail(String ipoName);


}
