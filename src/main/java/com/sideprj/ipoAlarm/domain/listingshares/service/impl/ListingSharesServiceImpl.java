package com.sideprj.ipoAlarm.domain.listingshares.service.impl;

import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepository;
import com.sideprj.ipoAlarm.domain.listingshares.service.ListingSharesService;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@RequiredArgsConstructor
@Service
@Slf4j
public class ListingSharesServiceImpl implements ListingSharesService {

    private final ListingSharesRepository listingSharesRepository;


    @Override
    @Transactional(readOnly = true)
    public PageResponseVo<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException {
        Page<ListingSharesGetAllDto> content = listingSharesRepository.fetchListingShares(requestVo, pageable);
        long total = content.getTotalElements();
        long totalPages = content.getTotalPages();
        return new PageResponseVo<>(
                content.getContent(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                total,
                totalPages
        );

    }
}
