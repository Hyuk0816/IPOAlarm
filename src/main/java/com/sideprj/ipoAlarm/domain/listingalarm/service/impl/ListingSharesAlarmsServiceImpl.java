package com.sideprj.ipoAlarm.domain.listingalarm.service.impl;

import com.sideprj.ipoAlarm.domain.alarm.constants.AlarmConstants;
import com.sideprj.ipoAlarm.domain.listingalarm.constants.ListingSharesAlarmsConstants;
import com.sideprj.ipoAlarm.domain.listingalarm.entity.ListingSharesAlarms;
import com.sideprj.ipoAlarm.domain.listingalarm.repository.ListingSharesAlarmsRepository;
import com.sideprj.ipoAlarm.domain.listingalarm.service.ListingSharesAlarmsService;
import com.sideprj.ipoAlarm.domain.listingshares.entity.ListingShares;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepository;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.domain.user.service.AuthService;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;
import com.sideprj.ipoAlarm.util.exception.AlarmAlreadyExistsException;
import com.sideprj.ipoAlarm.util.exception.ListingSharesAlarmEndDateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingSharesAlarmsServiceImpl implements ListingSharesAlarmsService {

    private final ListingSharesAlarmsRepository listingSharesAlarmsRepository;
    private final ListingSharesRepository listingSharesRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public void saveAlarm(String listingShares, User user) {
        ListingShares listingShare = listingSharesRepository.findByIpoName(listingShares);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authService.checkAuthentication(authentication);

        if(listingSharesAlarmsRepository.checkAlreadyExists(listingShares, user.getUserId())!=null){
            throw new AlarmAlreadyExistsException(AlarmConstants.msg_500, listingShares);
        }
        LocalDate now = LocalDate.now();

        if(now.isAfter(listingShare.getListingDate()) || now.equals(listingShare.getListingDate())){
            throw new ListingSharesAlarmEndDateException(ListingSharesAlarmsConstants.MSG_ALREADY_LISTING);
        }

        ListingSharesAlarms alarms = ListingSharesAlarms.builder()
                .listingShares(listingShare)
                .user(user)
                .listingDate(String.valueOf(listingShare.getListingDate()))
                .build();

        listingSharesAlarmsRepository.save(alarms);
    }

    @Override
    public Long countMyListingSharesAlarm(User user) {
        return listingSharesAlarmsRepository.countMyListingSharesAlarms(user.getUserId());
    }
}
