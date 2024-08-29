package com.sideprj.ipoAlarm.domain.ipodetail.repository;

import com.sideprj.ipoAlarm.domain.ipodetail.entity.IpoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IpoDetailRepository extends JpaRepository<IpoDetail, Long>, QuerydslPredicateExecutor, IpoDetailRepositoryCustom {

    IpoDetail findByIpoName(String name);

}
