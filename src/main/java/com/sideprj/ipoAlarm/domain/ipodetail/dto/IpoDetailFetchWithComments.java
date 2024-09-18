package com.sideprj.ipoAlarm.domain.ipodetail.dto;

import com.sideprj.ipoAlarm.domain.ipocomments.dto.IpoCommentsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IpoDetailFetchWithComments {

    private String ipoName;
    private String industry;
    private String representative;
    private String revenue;
    private String netProfit;
    private String totalOfferedShares;
    private String competitionRate;
    private String securities;
    private String confirmPrice;
    private List<IpoCommentsDto> ipoComments;

}
