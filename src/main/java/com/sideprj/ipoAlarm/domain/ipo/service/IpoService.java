package com.sideprj.ipoAlarm.domain.ipo.service;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.dto.IpoSearchRequestVo;
import com.sideprj.ipoAlarm.util.page.PageResponseVo;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IpoService {

    void save() throws IOException;

    PageResponseVo<IpoGetAllDto> fetchIpo(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws IOException, ParseException;

//    PageResponseVo<IpoGetAllDto> getAll(IpoSearchRequestVo searchRequestVo, Pageable pageable) throws IOException, ParseException;

}
