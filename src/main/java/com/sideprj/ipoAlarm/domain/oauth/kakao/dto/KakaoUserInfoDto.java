package com.sideprj.ipoAlarm.domain.oauth.kakao.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoUserInfoDto {

    private String id;
    private String nickName;
    private String email;
    private String image;
}
