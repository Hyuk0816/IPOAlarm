package com.sideprj.ipoAlarm.domain.alarm.repository;

import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, QuerydslPredicateExecutor, AlarmRepositoryCustom{

}
