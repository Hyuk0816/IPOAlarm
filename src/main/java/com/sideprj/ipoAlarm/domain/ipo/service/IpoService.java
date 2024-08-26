package com.sideprj.ipoAlarm.domain.ipo.service;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.vo.request.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;

public interface IpoService {


    PageResponseVo<IpoGetAllDto> fetchIpo(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws IOException, ParseException;




    void cacheWarming() throws ParseException;

}
