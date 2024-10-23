package com.sideprj.ipoAlarm.domain.ipocomments.service;

import com.sideprj.ipoAlarm.domain.ipocomments.vo.request.IpoCommentsRequest;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;

public interface IpoCommentsService {

    void comments(String ipoName, IpoCommentsRequest request, User user);
}
