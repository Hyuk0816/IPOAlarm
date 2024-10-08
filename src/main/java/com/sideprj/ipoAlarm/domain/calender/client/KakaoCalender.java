package com.sideprj.ipoAlarm.domain.calender.client;

//import com.sideprj.ipoAlarm.util.openfeign.OpenFeignConfig;
import jakarta.persistence.PostRemove;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "kakaoCalender",
        url = "https://kapi.kakao.com/v2/api/calendar/create/event"
        //configuration = OpenFeignConfig.class
)
public interface KakaoCalender {

    @PostMapping
    void createCalender(@RequestHeader("Authorization: Bearer ")String accessToken,@RequestBody Object calender);
}
