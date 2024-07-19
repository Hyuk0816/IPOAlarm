package com.sideprj.ipoAlarm.domain.ipo.repository;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IpoRepositoryCustom {

    Page<IpoGetAllDto> fetchIpoData(IpoSearchRequestVo searchRequestVo, Pageable pageable);

}
