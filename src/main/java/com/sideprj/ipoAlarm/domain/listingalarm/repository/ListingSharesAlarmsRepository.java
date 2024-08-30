package com.sideprj.ipoAlarm.domain.listingalarm.repository;

import com.sideprj.ipoAlarm.domain.listingalarm.entity.ListingSharesAlarms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ListingSharesAlarmsRepository extends JpaRepository<ListingSharesAlarms,Long>, QuerydslPredicateExecutor {


}
