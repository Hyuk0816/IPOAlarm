package com.sideprj.ipoAlarm.domain.ipocomments.repository;

import com.sideprj.ipoAlarm.domain.ipo.constant.IpoConstants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpoCommentsRepository extends JpaRepository<IpoConstants, Long> {
}
