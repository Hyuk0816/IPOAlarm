package com.sideprj.ipoAlarm.domain.user.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(
        name = "TokensInfo",
        description = "Schema to hold successful response Tokens information"
)
@Getter
@Builder
@AllArgsConstructor
public class AccessTokenResponse {
    @NotNull
    private String accessToken;
    private String type;
}
