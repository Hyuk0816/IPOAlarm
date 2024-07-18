package com.sideprj.ipoAlarm.domain.user.repository;

import com.sideprj.ipoAlarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> , QuerydslPredicateExecutor {

    Optional<User> findByEmail(String email);
}
