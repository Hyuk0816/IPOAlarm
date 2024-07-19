package com.sideprj.ipoAlarm.domain.ipo.mapper;

import com.sideprj.ipoAlarm.domain.ipo.dto.IpoGetAllDto;
import com.sideprj.ipoAlarm.domain.ipo.entity.Ipo;
import org.springframework.data.domain.Page;

import java.util.List;

public class IpoMapper {

    public static List<IpoGetAllDto> mapFromIpoListToIpoGetAllDtoList(Page<IpoGetAllDto> ipoList) {
        return ipoList.stream()
                .map(ipo -> new IpoGetAllDto(
                        ipo.getIpoName(),
                        ipo.getIpoPrice(),
                        ipo.getConfirmPrice(),
                        ipo.getCompetitionRate(),
                        ipo.getSecurities(),
                        ipo.getStartDate(),
                        ipo.getEndDate()
                )).toList();
    }
}
