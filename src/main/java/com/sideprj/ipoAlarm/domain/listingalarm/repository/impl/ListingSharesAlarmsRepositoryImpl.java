package com.sideprj.ipoAlarm.domain.listingalarm.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.listingalarm.entity.ListingSharesAlarms;
import com.sideprj.ipoAlarm.domain.listingalarm.entity.QListingSharesAlarms;
import com.sideprj.ipoAlarm.domain.listingalarm.repository.ListingSharesAlarmsRepositoryCustom;
import com.sideprj.ipoAlarm.domain.listingshares.entity.QListingShares;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyListingSharesAlarmsDto;
import com.sideprj.ipoAlarm.domain.user.entity.QUser;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sideprj.ipoAlarm.domain.listingalarm.entity.QListingSharesAlarms.*;
import static com.sideprj.ipoAlarm.domain.listingshares.entity.QListingShares.*;



public class ListingSharesAlarmsRepositoryImpl implements ListingSharesAlarmsRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public ListingSharesAlarmsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MyListingSharesAlarmsDto> fetchMyListingSharesAlarms(Long userID) {
        return queryFactory.select(Projections.fields(MyListingSharesAlarmsDto.class,
                listingSharesAlarms.listingShares.ipoName.as("listingShares"),
                listingSharesAlarms.listingDate.as("listingDate"),
                listingShares.offeringPrice.as("offeringPrice")))
                .from(listingSharesAlarms)
                .leftJoin(listingShares)
                .on(listingSharesAlarms.listingShares.ipoName.eq(listingShares.ipoName))
                .where(listingSharesAlarms.user.userId.eq(userID))
                .fetch();
    }

    @Override
    public ListingSharesAlarms checkAlreadyExists(String listingShares, Long userID) {
        return queryFactory.selectFrom(listingSharesAlarms)
                .where(listingSharesAlarms.listingShares.ipoName.eq(listingShares),
                        listingSharesAlarms.user.userId.eq(userID))
                .fetchOne();
    }
}
