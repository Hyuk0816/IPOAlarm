package com.sideprj.ipoAlarm.domain.message.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoMessage",
        url = "https://kapi.kakao.com/v2/api/talk/memo/default/send"
)
public interface KakaoMessage {

    @PostMapping
    ResponseEntity<String> sendMessage(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody MultiValueMap<String, String> body);
}
