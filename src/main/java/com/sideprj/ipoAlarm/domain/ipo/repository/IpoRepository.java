package com.sideprj.ipoAlarm.domain.ipo.repository;

import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IpoRepository extends JpaRepository<Ipo, Long>, QuerydslPredicateExecutor, IpoRepositoryCustom {
    Ipo findByIpoName(String name);

}
