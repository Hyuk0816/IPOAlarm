package com.sideprj.ipoAlarm.domain.ipocomments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IpoCommentsDto {

    @Schema(description = "댓글아이디")
    private Long id;

    @Schema(description = "유저닉네임")
    private String nickName;

    @Schema(description = "유저 사진")
    private String profile;

    @Schema(description = "공모주 상세 페이지 댓글")
    private String ipoComments;

    @Schema(description = "게시한 시간 ")
    private String regDate;

}
