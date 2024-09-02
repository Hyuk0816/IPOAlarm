package com.sideprj.ipoAlarm.domain.ipo.repository;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface IpoRepositoryCustom {

    Page<IpoGetAllDto> fetchIpoData(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws ParseException;


}
