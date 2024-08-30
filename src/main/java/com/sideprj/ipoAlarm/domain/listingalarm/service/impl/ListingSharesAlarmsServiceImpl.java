package com.sideprj.ipoAlarm.domain.listingalarm.service.impl;

import com.sideprj.ipoAlarm.domain.listingalarm.entity.ListingSharesAlarms;
import com.sideprj.ipoAlarm.domain.listingalarm.repository.ListingSharesAlarmsRepository;
import com.sideprj.ipoAlarm.domain.listingalarm.service.ListingSharesAlarmsService;
import com.sideprj.ipoAlarm.domain.listingshares.entity.ListingShares;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepository;
import com.sideprj.ipoAlarm.domain.listingshares.service.ListingSharesService;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingSharesAlarmsServiceImpl implements ListingSharesAlarmsService {

    private final ListingSharesAlarmsRepository listingSharesAlarmsRepository;
    private final ListingSharesRepository listingSharesRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveAlarm(String listingShares) {
        ListingShares listingShare = listingSharesRepository.findByIpoName(listingShares);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        ListingSharesAlarms alarms = ListingSharesAlarms.builder()
                .listingShares(listingShare)
                .user(user)
                .listingDate(String.valueOf(listingShare.getListingDate()))
                .build();

        listingSharesAlarmsRepository.save(alarms);
    }
}
