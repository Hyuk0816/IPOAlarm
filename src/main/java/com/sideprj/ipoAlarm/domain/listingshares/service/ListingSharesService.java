package com.sideprj.ipoAlarm.domain.listingshares.service;

import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.dto.OfferingToOpeningPriceMonthlyProfitDto;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ListingSharesService {

    PageResponseVo<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException;

    OfferingToOpeningPriceMonthlyProfitDto previousMonthProfitService();

    List<Double> monthlyProfit(Integer year);
}
