package com.sideprj.ipoAlarm.domain.ipocomments.service.impl;

import com.sideprj.ipoAlarm.domain.ipocomments.entity.IpoComments;
import com.sideprj.ipoAlarm.domain.ipocomments.repository.IpoCommentsRepository;
import com.sideprj.ipoAlarm.domain.ipocomments.service.IpoCommentsService;
import com.sideprj.ipoAlarm.domain.ipocomments.vo.request.IpoCommentsRequest;
import com.sideprj.ipoAlarm.domain.ipodetail.entity.IpoDetail;
import com.sideprj.ipoAlarm.domain.ipodetail.repository.IpoDetailRepository;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class IpoCommentsServiceImpl implements IpoCommentsService {

    private final IpoDetailRepository ipodetailRepository;
    private final UserRepository userRepository;
    private final IpoCommentsRepository ipoCommentsRepository;

    @Override
    public void comments(String ipoName,IpoCommentsRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        IpoDetail ipoDetail = ipodetailRepository.findByIpoName(ipoName);

        String regDate = DateFormatter.LocalDateTimeformat(LocalDateTime.now());

        IpoComments comment = IpoComments.builder()
                .ipoName(ipoDetail)
                .userId(user)
                .comment(request.getIpoComment())
                .regDate(regDate)
                .build();

        ipoCommentsRepository.save(comment);
    }
}
