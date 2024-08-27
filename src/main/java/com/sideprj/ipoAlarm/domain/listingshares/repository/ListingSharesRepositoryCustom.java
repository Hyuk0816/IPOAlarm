package com.sideprj.ipoAlarm.domain.listingshares.repository;

import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.dto.OfferingToOpeningPriceMonthlyProfitDto;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface ListingSharesRepositoryCustom {

    Page<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException;

    OfferingToOpeningPriceMonthlyProfitDto previousMonthProfit();

    List<Double> monthlyProfit(Integer year);
}
