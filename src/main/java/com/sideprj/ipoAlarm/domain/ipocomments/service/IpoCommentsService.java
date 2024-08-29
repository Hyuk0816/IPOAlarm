package com.sideprj.ipoAlarm.domain.ipocomments.service;

import com.sideprj.ipoAlarm.domain.ipocomments.vo.request.IpoCommentsRequest;

public interface IpoCommentsService {

    void comments(String ipoName,IpoCommentsRequest request);
}
