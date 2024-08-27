package com.sideprj.ipoAlarm.domain.ipocomments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IpoCommentsDto {

    @Schema(description = "공모주 상세 페이지 댓글")
    private String ipoComments;

}
