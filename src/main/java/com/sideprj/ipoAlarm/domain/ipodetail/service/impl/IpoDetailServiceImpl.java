package com.sideprj.ipoAlarm.domain.ipodetail.service.impl;

import com.sideprj.ipoAlarm.domain.ipocomments.dto.IpoCommentsDto;
import com.sideprj.ipoAlarm.domain.ipocomments.repository.IpoCommentsRepository;
import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;
import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchWithComments;
import com.sideprj.ipoAlarm.domain.ipodetail.repository.IpoDetailRepository;
import com.sideprj.ipoAlarm.domain.ipodetail.service.IpoDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class IpoDetailServiceImpl implements IpoDetailService {

    private final IpoDetailRepository ipoDetailRepository;
    private final IpoCommentsRepository ipoCommentsRepository;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "getIpoDetail", keyGenerator = "customKeyGenerator")
    public IpoDetailFetchWithComments getIpoDetail(String ipoName) {

        IpoDetailFetchDto ipoDetailFetchDto = ipoDetailRepository.fetchIpoDetail(ipoName);

        List<IpoCommentsDto> byIpoName = ipoCommentsRepository.findByIpoName(ipoName);

        return IpoDetailFetchWithComments.builder()
                .ipoName(ipoDetailFetchDto.getIpoName())
                .revenue(ipoDetailFetchDto.getRevenue())
                .industry(ipoDetailFetchDto.getIndustry())
                .representative(ipoDetailFetchDto.getRepresentative())
                .netProfit(ipoDetailFetchDto.getNetProfit())
                .totalOfferedShares(ipoDetailFetchDto.getTotalOfferedShares())
                .ipoComments(byIpoName)
                .build();

    }
}
