package com.sideprj.ipoAlarm.domain.calender.client;

//import com.sideprj.ipoAlarm.config.aws.OpenFeignConfig;
import jakarta.persistence.PostRemove;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "kakaoCalender",
        url = "https://kapi.kakao.com/v2/api/calendar/create/event"
        //configuration = OpenFeignConfig.class
)
public interface KakaoCalender {

    @PostMapping
    void createCalender(@RequestBody Object calender);
}
