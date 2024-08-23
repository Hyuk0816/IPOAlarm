package com.sideprj.ipoAlarm.domain.listingshares.repository.impl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.listingshares.dto.ListingSharesGetAllDto;
import com.sideprj.ipoAlarm.domain.listingshares.entity.ListingShares;
import com.sideprj.ipoAlarm.domain.listingshares.repository.ListingSharesRepositoryCustom;
import com.sideprj.ipoAlarm.domain.listingshares.vo.request.ListingSharesRequestVo;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

import static com.sideprj.ipoAlarm.domain.listingshares.entity.QListingShares.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ListingSharesRepositoryImpl implements ListingSharesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ListingSharesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<ListingSharesGetAllDto> fetchListingShares(ListingSharesRequestVo requestVo, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        //공모주 이름
        addIpoNameCondition(builder,requestVo.getIpoName());
        //전일비 조건
        addChangeRatePreviousCondition(builder, requestVo);
        //공모가격 대비 가격 변동율 조건
        addChangeRateOfferingPriceCondition(builder, requestVo);
        //공모가격 대비 상장일 당일 가격 변동율 조건
        addChangeRateOpeningToOfferingPriceCondition(builder, requestVo);
        //상장일 조건
        addListingDateCondition(builder,requestVo);

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

    // IPO 이름 조건 추가 메서드
    private void addIpoNameCondition(BooleanBuilder whereBuilder, String ipoName) {
        if (ipoName != null && !ipoName.isEmpty()) {
            whereBuilder.and(listingShares.ipoName.contains(ipoName));
        }
    }

    // 전일 대비 가격 변동율 조건 추가 메서드
    private void addChangeRatePreviousCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        //둘다 null이 아닐 때
        if (requestVo.getMinChangeRatePrevious() != null && requestVo.getMaxChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.between(requestVo.getMinChangeRatePrevious(),requestVo.getMaxChangeRatePrevious()));
        } else if (requestVo.getMinChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.goe(requestVo.getMinChangeRatePrevious())); // min 조건만 있을 때
        } else if (requestVo.getMaxChangeRatePrevious() != null) {
            whereBuilder.and(listingShares.changeRatePrevious.loe(requestVo.getMaxChangeRatePrevious())); // max 조건만 있을 때
        }
    }


    // 공모가격 대비 가격 변동율 조건 추가 메서드
    private void addChangeRateOfferingPriceCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
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
    private void addChangeRateOpeningToOfferingPriceCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        //둘다 null이 아닐 때
        if (requestVo.getMinChangeRateOpeningToOfferingPrice() != null && requestVo.getMaxChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.between(requestVo.getMinChangeRateOpeningToOfferingPrice(), requestVo.getMaxChangeRateOpeningToOfferingPrice()));
        } else if (requestVo.getMinChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.goe(requestVo.getMinChangeRateOpeningToOfferingPrice())); // min 조건만 있을 때
        } else if (requestVo.getMaxChangeRateOpeningToOfferingPrice() != null) {
            whereBuilder.and(listingShares.changeRateOpeningToOfferingPrice.loe(requestVo.getMaxChangeRateOpeningToOfferingPrice())); // max 조건만 있을 때
        }
    }

    private void addListingDateCondition(BooleanBuilder whereBuilder, ListingSharesRequestVo requestVo) {
        if(requestVo.getListingStartDate() !=null && requestVo.getListingendDate() != null){
            whereBuilder.and(listingShares.listingDate.between(requestVo.getListingStartDate(),requestVo.getListingendDate()));
        } else if(requestVo.getListingStartDate() != null){
            whereBuilder.and(listingShares.listingDate.after(requestVo.getListingStartDate()));
        } else if (requestVo.getListingendDate() != null) {
            whereBuilder.and(listingShares.listingDate.before(requestVo.getListingendDate()));
        }
    }

}
