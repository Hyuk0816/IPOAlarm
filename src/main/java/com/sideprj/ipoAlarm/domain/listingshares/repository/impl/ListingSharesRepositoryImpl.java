package com.sideprj.ipoAlarm.domain.listingshares.repository.impl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.dto.MostExpensiveListingShare;
import com.sideprj.ipoAlarm.domain.listingshares.dto.MostValuableListingShare;
import com.sideprj.ipoAlarm.domain.listingshares.dto.OfferingToOpeningPriceMonthlyProfitDto;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepositoryCustom;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import static com.sideprj.ipoAlarm.domain.listingshares.entity.QListingShares.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ListingSharesRepositoryImpl implements ListingSharesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ListingSharesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) throws ParseException {

        BooleanBuilder builder = new BooleanBuilder();
        //공모주 이름
        ipoNameCondition(builder,requestVo.getIpoName());
        //전일비 조건
        changeRatePreviousCondition(builder, requestVo);
        //공모가격 대비 가격 변동율 조건
        changeRateOfferingPriceCondition(builder, requestVo);
        //공모가격 대비 상장일 당일 가격 변동율 조건
        changeRateOpeningToOfferingPriceCondition(builder, requestVo);
        //상장일 조건
        listingDateCondition(builder,requestVo);

        List<ListingSharesGetAllDto> content = queryFactory
                .select(Projections.fields(ListingSharesGetAllDto.class,
                        listingShares.ipoName.as("ipoName"),
                        listingShares.listingDate.as("listingDate"),
                        listingShares.currentPrice.as("currentPrice"),
                        listingShares.changeRatePrevious.as("changeRatePrevious"),
                        listingShares.offeringPrice.as("offeringPrice"),
                        listingShares.changeRateOfferingPrice.as("changeRateOfferingPrice"),
                        listingShares.openingPrice.as("openingPrice"),
                        listingShares.changeRateOpeningToOfferingPrice.as("changeRateOpeningToOfferingPrice"),
                        listingShares.closingPriceFirstDay.as("closingPriceFirstDay")
                ))
                .from(listingShares)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(builder)
                .orderBy(listingShares.listingDate.desc())
                .fetch();

            Long countQuery = queryFactory.select(listingShares.count()).from(listingShares)
                .where(builder).fetchOne();

        return PageableExecutionUtils.getPage(content,pageable, Objects.requireNonNull(countQuery)::longValue);
    }

    @Override
    public OfferingToOpeningPriceMonthlyProfitDto previousMonthProfit() {

        LocalDate date = LocalDate.now();

        LocalDate start = previousDateFirst(date);
        LocalDate end = previousDateLast(date);

        return queryFactory.select(Projections.fields(OfferingToOpeningPriceMonthlyProfitDto.class,
                listingShares.changeRateOpeningToOfferingPrice.avg().as("monthlyProfit")
        )).from(listingShares).where(listingShares.listingDate.between(start, end)).fetchOne();
    }

    @Override
    public List<Double> monthlyProfit(Integer year) {

        List<Double> monthlyProfitList = new ArrayList<>();
        for(int i = 1; i<13; i++){
            LocalDate startDate = LocalDate.of(year, i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            Double avgProfit = queryFactory.select(listingShares.changeRateOpeningToOfferingPrice.avg())
                    .from(listingShares)
                    .where(listingShares.listingDate.between(startDate, endDate))
                    .fetchOne();

            monthlyProfitList.add(avgProfit != null ? avgProfit : 0);
        }
        return monthlyProfitList;
    }

    @Override
    public List<MostValuableListingShare> mostValuableListingShares() {
        return queryFactory.select(Projections.fields(MostValuableListingShare.class,
                listingShares.ipoName.as("ipoName"),
                listingShares.changeRateOpeningToOfferingPrice.as("changeRateOpeningToOfferingPrice")))
                .from(listingShares)
                .orderBy(listingShares.changeRateOpeningToOfferingPrice.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public MostExpensiveListingShare mostExpensiveListingShares() {
        return queryFactory.select(Projections.fields(MostExpensiveListingShare.class,
                listingShares.ipoName.as("ipoName"),
                listingShares.offeringPrice.as("offeringPrice"),
                listingShares.currentPrice.as("currentPrice"),
                listingShares.changeRateOfferingPrice.as("changeRateOfferingPrice")))
                .from(listingShares)
                .orderBy(listingShares.changeRateOfferingPrice.desc())
                .limit(1)
                .fetchOne();
    }

    // IPO 이름 조건 추가 메서드
    private void ipoNameCondition(BooleanBuilder whereBuilder, String ipoName) {
        if (ipoName != null && !ipoName.isEmpty()) {
            whereBuilder.and(listingShares.ipoName.contains(ipoName));
        }
    }

    // 전일 대비 가격 변동율 조건 추가 메서드
    private void changeRatePreviousCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        //둘다 null이 아닐 때
        if (requestVo.getMinChangeRatePrevious() != null && requestVo.getMaxChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.between(requestVo.getMinChangeRatePrevious(),requestVo.getMaxChangeRatePrevious()));
        } else if (requestVo.getMinChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.goe(requestVo.getMinChangeRatePrevious())); // min 조건만 있을 때
        } else if (requestVo.getMaxChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.loe(requestVo.getMaxChangeRatePrevious())); // max 조건만 있을 때
        }
    }


    // 공모가격 대비 현재가격 변동율 조건 추가 메서드
    private void changeRateOfferingPriceCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        //둘다 null이 아닐 때
        if (requestVo.getMinChangeRateOfferingPrice() != null && requestVo.getMaxChangeRateOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOfferingPrice.between(requestVo.getMinChangeRateOfferingPrice(), requestVo.getMaxChangeRateOfferingPrice()));
        } else if (requestVo.getMinChangeRateOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOfferingPrice.goe(requestVo.getMinChangeRateOfferingPrice())); // min 조건만 있을 때
        } else if (requestVo.getMaxChangeRateOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOfferingPrice.loe(requestVo.getMaxChangeRateOfferingPrice())); // max 조건만 있을 때
        }
    }

    // 공모가격 대비 상장일 당일 가격 변동율 조건 추가 메서드
    private void changeRateOpeningToOfferingPriceCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        //둘다 null이 아닐 때
        if (requestVo.getMinChangeRateOpeningToOfferingPrice() != null && requestVo.getMaxChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.between(requestVo.getMinChangeRateOpeningToOfferingPrice(), requestVo.getMaxChangeRateOpeningToOfferingPrice()));
        } else if (requestVo.getMinChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.goe(requestVo.getMinChangeRateOpeningToOfferingPrice())); // min 조건만 있을 때
        } else if (requestVo.getMaxChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.loe(requestVo.getMaxChangeRateOpeningToOfferingPrice())); // max 조건만 있을 때
        }
    }

    private void listingDateCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) throws ParseException {
        LocalDate listingStartDate = DateFormatter.format(requestVo.getListingStartDate());
        LocalDate listingEndDate = DateFormatter.format(requestVo.getListingEndDate());

        if(requestVo.getListingStartDate() !=null && requestVo.getListingEndDate() != null){
            whereBuilder.and(listingShares.listingDate.between(listingStartDate,listingEndDate));
        } else if(requestVo.getListingStartDate() != null){
            whereBuilder.and(listingShares.listingDate.goe(listingStartDate));
        } else if (requestVo.getListingEndDate() != null) {
            whereBuilder.and(listingShares.listingDate.loe(listingEndDate));
        }
    }

    private LocalDate previousDateFirst(LocalDate date){
        return date.minusMonths(1).withDayOfMonth(1);
    }

    private LocalDate previousDateLast(LocalDate date){
       return date.minusMonths(1).withDayOfMonth(date.minusMonths(1).lengthOfMonth());
    }

}
