package com.sideprj.ipoAlarm.domain.oauth.repository;

import com.sideprj.ipoAlarm.domain.oauth.entity.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KakaoTokenRepository extends JpaRepository<KakaoToken, Long>, QuerydslPredicateExecutor {

    KakaoToken findByUserUserId(Long userId);



}
