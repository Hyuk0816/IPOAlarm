package com.sideprj.ipoAlarm.domain.ipo.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sideprj.ipoAlarm.domain.ipo.entity.QIpo.*;
import static org.springframework.util.StringUtils.hasText;


import java.util.List;

public class IpoRepositoryImpl implements IpoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public IpoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<IpoGetAllDto> fetchIpoData(IpoSearchRequestVo searchRequestVo, Pageable pageable) {
        List<IpoGetAllDto> content = queryFactory
                .select(Projections.fields(IpoGetAllDto.class,
                        ipo.ipoName.as("ipoName"),
                        ipo.ipoPrice.as("ipoPrice"),
                        ipo.confirmPrice.as("confirmPrice"),
                        ipo.competitionRate.as("competitionRate"),
                        ipo.securities.as("securities"),
                        ipo.startDate.as("startDate"),
                        ipo.endDate.as("endDate")
                ))
                .from(ipo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(containIpoName(searchRequestVo.getIpoName()),
                        containStartDate(searchRequestVo),
                        containEndDate(searchRequestVo))
                .orderBy(ipo.startDate.desc())
                .fetch();
        JPAQuery<Ipo> countQuery = queryFactory.select(ipo).from(ipo)
                .where(containIpoName(searchRequestVo.getIpoName()),
                        containStartDate(searchRequestVo),
                        containEndDate(searchRequestVo));

        return PageableExecutionUtils.getPage(content, pageable,countQuery::fetchCount);
    }

    private BooleanExpression containIpoName(String ipoName){return hasText(ipoName) ? ipo.ipoName.contains(ipoName) : null;}



    private BooleanExpression containStartDate(IpoSearchRequestVo searchRequestVo) {
        if (searchRequestVo.getSearchStartDate() != null) {
            if (searchRequestVo.getSearchEndDate() != null) {
                return ipo.startDate.between(searchRequestVo.getSearchStartDate(), searchRequestVo.getSearchEndDate());
            } else {
                return ipo.startDate.after(searchRequestVo.getSearchStartDate());
            }
        }
        return null;
    }

    private BooleanExpression containEndDate(IpoSearchRequestVo searchRequestVo) {
        if (searchRequestVo.getSearchEndDate() != null && searchRequestVo.getSearchStartDate() == null) {
            return ipo.startDate.before(searchRequestVo.getSearchEndDate());
        }
        return null;
    }



}
