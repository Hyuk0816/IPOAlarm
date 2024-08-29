package com.sideprj.ipoAlarm.domain.ipocomments.repository;

import com.sideprj.ipoAlarm.domain.ipocomments.dto.IpoCommentsDto;
import com.sideprj.ipoAlarm.domain.ipocomments.entity.IpoComments;

import java.util.List;

public interface IpoCommentsRepositoryCustom {

    List<IpoCommentsDto> findByIpoName(String ipoName);
}
