package com.sideprj.ipoAlarm.domain.ipodetail.repository.impl;



import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.ipo.entity.QIpo;
import com.sideprj.ipoAlarm.domain.ipocomments.dto.IpoCommentsDto;
import com.sideprj.ipoAlarm.domain.ipocomments.entity.QIpoComments;
import com.sideprj.ipoAlarm.domain.ipodetail.dto.IpoDetailFetchDto;
import com.sideprj.ipoAlarm.domain.ipodetail.repository.IpoDetailRepositoryCustom;
import jakarta.persistence.EntityManager;

import static com.sideprj.ipoAlarm.domain.ipo.entity.QIpo.*;
import static com.sideprj.ipoAlarm.domain.ipocomments.entity.QIpoComments.*;
import static com.sideprj.ipoAlarm.domain.ipodetail.entity.QIpoDetail.*;

public class IpoDetailRepositoryImpl implements IpoDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IpoDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override //나중에 댓글도 추가해서 join 해서 가져와야 한다.
    public IpoDetailFetchDto fetchIpoDetail(String ipoName) {

        return queryFactory.select(Projections.fields(IpoDetailFetchDto.class,
                ipoDetail.ipoName.as("ipoName"),
                ipoDetail.industry.as("industry"),
                ipoDetail.representative.as("representative"),
                ipoDetail.revenue.as("revenue"),
                ipoDetail.netProfit.as("netProfit"),
                        ipo.competitionRate.as("competitionRate"),
                        ipo.securities.as("securities"),
                        ipo.confirmPrice.as("confirmPrice")))
                .from(ipoDetail)
                .join(ipo)
                .on(ipoDetail.ipoName.eq(ipo.ipoName))
                .where(ipoDetail.ipoName.eq(ipoName))
                .fetchOne();
    }
}
