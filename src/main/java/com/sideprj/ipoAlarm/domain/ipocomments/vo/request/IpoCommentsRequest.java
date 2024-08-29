package com.sideprj.ipoAlarm.domain.ipocomments.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@Schema(
        name = "Registration IpoComments Request",
        description = "Schema to hold successful request Registration IpoComments Request"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpoCommentsRequest {

    @Schema(description = "댓글", example = "이 공모주 떡상 할 듯 ㄹㅇ")
    private String ipoComment;

}
