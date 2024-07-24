package com.sideprj.ipoAlarm.domain.oauth.kakao.client;

import com.sideprj.ipoAlarm.domain.oauth.kakao.dto.KakaoResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoResource",
        url = "https://kapi.kakao.com")
public interface KakaoResourceApi {
    @GetMapping(
            value = "/v2/user/me")
    KakaoResourceDto kakaoGetResource(@RequestHeader("Authorization") String accessToken);

}
