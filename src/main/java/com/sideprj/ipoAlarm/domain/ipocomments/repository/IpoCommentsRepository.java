package com.sideprj.ipoAlarm.domain.ipocomments.repository;

import com.sideprj.ipoAlarm.domain.ipocomments.entity.IpoComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IpoCommentsRepository extends JpaRepository<IpoComments, Long> , QuerydslPredicateExecutor, IpoCommentsRepositoryCustom {

}
