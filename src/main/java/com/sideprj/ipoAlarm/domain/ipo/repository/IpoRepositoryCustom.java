package com.sideprj.ipoAlarm.domain.ipo.repository;

import com.sideprj.ipoAlarm.domain.alarm.entity.Alarm;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.domain.mypage.MyAlarmDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface IpoRepositoryCustom {

    Page<IpoGetAllDto> fetchIpoData(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws ParseException;


}
