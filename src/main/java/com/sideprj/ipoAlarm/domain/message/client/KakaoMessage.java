package com.sideprj.ipoAlarm.domain.message.client;

import com.sideprj.ipoAlarm.config.aws.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@FeignClient(
        name = "KakaoMessage",
        url = "https://kapi.kakao.com/v2/api/talk/memo/default/send",
        configuration = OpenFeignConfig.class
)
public interface KakaoMessage {

    @PostMapping
    void sendMessage(@RequestBody Object template_object);
}
