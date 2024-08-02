package com.sideprj.ipoAlarm.domain.message.client;

import com.sideprj.ipoAlarm.config.openfeign.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "KakaoMessage",
        url = "https://kapi.kakao.com/v2/api/talk/memo/default/send",
        configuration = OpenFeignConfig.class
)
public interface KakaoMessage {

    @PostMapping
    void sendMessage(@RequestBody Object template_object);
}
