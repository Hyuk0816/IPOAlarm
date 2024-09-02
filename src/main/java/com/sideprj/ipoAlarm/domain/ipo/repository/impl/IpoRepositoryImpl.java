package com.sideprj.ipoAlarm.domain.ipo.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.ipo.repository.IpoRepositoryCustom;
import com.sideprj.ipoAlarm.util.converter.DateFormatter;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sideprj.ipoAlarm.domain.ipo.entity.QIpo.*;
import static org.springframework.util.StringUtils.hasText;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class IpoRepositoryImpl implements IpoRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(IpoRepositoryImpl.class);
    private final JPAQueryFactory queryFactory;

    public IpoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<IpoGetAllDto> fetchIpoData(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        ipoNameCondition(builder, searchRequestVo.getIpoName());
        ipoDateCondition(builder, searchRequestVo);

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
                .where(builder)
                .orderBy(ipo.startDate.desc())
                .fetch();

        Long countQuery = queryFactory.select(ipo.count()).from(ipo).where(builder).fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, Objects.requireNonNull(countQuery)::longValue);
    }

    //공모주 이름 조회 조건
    private void ipoNameCondition(BooleanBuilder builder, String ipoName){
        if(ipoName != null  && !ipoName.isEmpty()){
            builder.and(ipo.ipoName.contains(ipoName));
        }
    }
    //공모주 청약 일자 조회 조건
    private void ipoDateCondition(BooleanBuilder builder, IpoSearchRequestVo searchRequestVo) throws ParseException {
        Date startDate = DateFormatter.convertDate(searchRequestVo.getSearchStartDate());
        Date endDate = endDateTimeSet(DateFormatter.convertDate(searchRequestVo.getSearchEndDate()));

        if(startDate != null && endDate != null){
            builder.and(ipo.startDate.goe(startDate)).and(ipo.endDate.loe(endDate));
        }
        else if(startDate != null){
            builder.and(ipo.startDate.goe(startDate));
        }
        else if(endDate != null){
            builder.and(ipo.endDate.loe(endDate));
        }
    }

    private Date endDateTimeSet(Date endDate){
        if(endDate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            endDate = calendar.getTime();
            return endDate;
        }else{
            return null;
        }
    }
}
