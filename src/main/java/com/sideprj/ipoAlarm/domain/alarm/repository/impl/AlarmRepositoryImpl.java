package com.sideprj.ipoAlarm.domain.alarm.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.alarm.dto.AlarmExitsDto;
import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.alarm.entity.QAlarm;
import com.sideprj.ipoAlarm.domain.alarm.repository.AlarmRepositoryCustom;
import com.sideprj.ipoAlarm.domain.ipo.entity.QIpo;
import com.sideprj.ipoAlarm.domain.user.entity.QUser;
import jakarta.persistence.EntityManager;

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
                .leftJoin(alarm.ipo, ipo)
                .leftJoin(alarm.user, user)
                .where(alarm.ipo.ipoName.eq(ipoName),
                        alarm.user.userId.eq(userId))
                .fetchOne();
    }



}
