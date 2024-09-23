package com.sideprj.ipoAlarm.domain.listingshares.service.impl;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.dto.MostExpensiveListingShare;
import com.sideprj.ipoAlarm.domain.listingshares.dto.MostValuableListingShare;
import com.sideprj.ipoAlarm.domain.listingshares.dto.OfferingToOpeningPriceMonthlyProfitDto;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepository;
import com.sideprj.ipoAlarm.domain.listingshares.service.ListingSharesService;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
import com.sideprj.ipoAlarm.util.exception.EndDateSearchException;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class ListingSharesServiceImpl implements ListingSharesService {

    private final ListingSharesRepository listingSharesRepository;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "listingSharesGetAll", keyGenerator = "customKeyGenerator")
    public PageResponseVo<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException {
        Page<ListingSharesGetAllDto> content = listingSharesRepository.fetchListingShares(requestVo, pageable);
        long total = content.getTotalElements();
        long totalPages = content.getTotalPages();


        Date start = DateFormatter.convertDate(requestVo.getListingStartDate());
        Date end = DateFormatter.convertDate(requestVo.getListingEndDate());

        // start와 end가 모두 null이 아닐 경우에만 비교
        if (start != null && end != null && start.after(end)) {
            throw new EndDateSearchException(IpoConstants.END_DATE_EXCEPTION);
        }

        return new PageResponseVo<>(
                content.getContent(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                total,
                totalPages
        );

    }
    //전월 이윤
    @Override
    public OfferingToOpeningPriceMonthlyProfitDto previousMonthProfitService() {
        OfferingToOpeningPriceMonthlyProfitDto profit = listingSharesRepository.previousMonthProfit();
        profit.setMonthlyProfit(Math.ceil(profit.getMonthlyProfit()));
        return profit;
    }

    //연도별 이윤
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "monthlyProfit", keyGenerator = "customKeyGenerator")
    public List<Double> monthlyProfit(Integer year) {
        return listingSharesRepository.monthlyProfit(year);
    }

    @Override
    public List<MostValuableListingShare> mostValuableListingShares() {
        return listingSharesRepository.mostValuableListingShares();
    }

    @Override
    public MostExpensiveListingShare mostExpensiveListingShare() {
        return listingSharesRepository.mostExpensiveListingShares();
    }
}
