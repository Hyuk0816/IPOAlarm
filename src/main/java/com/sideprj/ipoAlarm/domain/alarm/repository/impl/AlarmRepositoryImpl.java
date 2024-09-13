package com.sideprj.ipoAlarm.domain.alarm.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepositoryCustom;
import com.sideprj.ipoAlarm.domain.mypage.dto.MyAlarmDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.sideprj.ipoAlarm.domain.alarm.entity.QAlarm.*;
import static com.sideprj.ipoAlarm.domain.ipo.entity.QIpo.*;
import static com.sideprj.ipoAlarm.domain.user.entity.QUser.*;

public class AlarmRepositoryImpl implements AlarmRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AlarmRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Alarm checkIfAlarmExists(String ipoName, Long userId) {

        return queryFactory.selectFrom(alarm)
                .where(alarm.ipo.ipoName.eq(ipoName),
                        alarm.user.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public List<Alarm> findByUserId(Long id) {
        return queryFactory.selectFrom(alarm)
                .where(alarm.user.userId.eq(id))
                .fetch();
    }

    @Override
    public List<MyAlarmDto> fetchMyAlarms(Long userId) {
        return queryFactory
                .select(Projections.fields(MyAlarmDto.class,
                        ipo.ipoName.as("ipoName"),
                        ipo.ipoPrice.as("ipoPrice"),
                        ipo.confirmPrice.as("confirmPrice"), // Alarm에서 confirmPrice 가져오기
                        ipo.securities.as("securities"),
                        ipo.startDate.as("startDate") // Alarm에서 startDate 가져오기
                ))
                .from(ipo)
                .leftJoin(alarm)
                .on(ipo.ipoName.eq(alarm.ipo.ipoName))
                .where(alarm.user.userId.eq(userId))
                .orderBy(ipo.startDate.desc())
                .fetch();
    }


}
