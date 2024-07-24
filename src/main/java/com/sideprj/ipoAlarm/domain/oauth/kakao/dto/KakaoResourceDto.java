package com.sideprj.ipoAlarm.domain.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoResourceDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("kakao_account")
    private Object kakaoAccountMap;


}
