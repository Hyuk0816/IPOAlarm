package com.sideprj.ipoAlarm.domain.oauth.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideprj.ipoAlarm.domain.oauth.entity.KakaoToken;
import com.sideprj.ipoAlarm.domain.oauth.entity.QKakaoToken;
import com.sideprj.ipoAlarm.domain.oauth.repository.KakaoTokenRepositoryCustom;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import static com.sideprj.ipoAlarm.domain.oauth.entity.QKakaoToken.*;

@RequiredArgsConstructor
public class KakaoRepositoryImpl implements KakaoTokenRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public KakaoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
