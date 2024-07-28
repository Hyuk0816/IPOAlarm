package com.sideprj.ipoAlarm.domain.message.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoMessage",
        url = "https://kapi.kakao.com/v2/api/talk/memo/default/send"
)
public interface KakaoMessage {

    @PostMapping
    void sendMessage(@RequestHeader HttpHeaders headers,
                     @RequestBody Object template_object);
}
