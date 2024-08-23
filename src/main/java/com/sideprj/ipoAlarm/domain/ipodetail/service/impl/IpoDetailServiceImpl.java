package com.sideprj.ipoAlarm.domain.ipodetail.service.impl;

import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;
import com.sideprj.ipoAlarm.domain.ipodetail.repository.IpoDetailRepository;
import com.sideprj.ipoAlarm.domain.ipodetail.service.IpoDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class IpoDetailServiceImpl implements IpoDetailService {

    private final IpoDetailRepository ipoDetailRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "getIpoDetail", keyGenerator = "customKeyGenerator")
    public IpoDetailFetchDto getIpoDetail(String ipoName) {
        return ipoDetailRepository.fetchIpoDetail(ipoName);
    }
}
