package com.sideprj.ipoAlarm.domain.ipo.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepositoryCustom;
import com.sideprj.ipoAlarm.domain.mypage.MyAlarmDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sideprj.ipoAlarm.domain.alarm.entity.QAlarm.alarm;
import static com.sideprj.ipoAlarm.domain.ipo.entity.QIpo.*;
import static org.springframework.util.StringUtils.hasText;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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

    @Override
    public List<MyAlarmDto> fetchMyAlarm(List<Alarm> alarms) {
        return alarms.stream()
                .map(alarm -> {
                    String ipoName = alarm.getIpo().getIpoName();

                    // QueryDSL을 사용하여 MyAlarmDto 생성
                    return queryFactory
                            .select(Projections.fields(MyAlarmDto.class,
                                    ipo.ipoName.as("ipoName"),
                                    ipo.ipoPrice.as("ipoPrice"),
                                    ipo.confirmPrice.as("confirmPrice"), // Alarm에서 confirmPrice 가져오기
                                    ipo.securities.as("securities"),
                                    ipo.startDate.as("startDate") // Alarm에서 startDate 가져오기
                            ))
                            .from(ipo)
                            .where(ipo.ipoName.eq(ipoName))
                            .orderBy(ipo.startDate.desc())
                            .fetchOne();
                })
                .collect(Collectors.toList()); // Stream을 List로 변환
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
