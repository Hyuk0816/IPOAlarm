package com.sideprj.ipoAlarm.domain.listingshares.repository;

import com.sideprj.ipoAlarm.domain.listingshares.entity.ListingShares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ListingSharesRepository extends JpaRepository<ListingShares, Long> , QuerydslPredicateExecutor, ListingSharesRepositoryCustom {


}
