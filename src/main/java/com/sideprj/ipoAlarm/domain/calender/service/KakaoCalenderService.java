package com.sideprj.ipoAlarm.domain.calender.service;

import com.sideprj.ipoAlarm.domain.calender.client.KakaoCalender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoCalenderService {

    private final KakaoCalender kakaoCalender;

    public void calenderSetting(){
        JSONObject title = new JSONObject();
        title.put("title", "test");

        JSONObject time = new JSONObject();
        time.put("start_at", "2024-08-02T03:00:00Z");
        time.put("end_at", "2024-08-03T03:00:00Z");
        time.put("time_zone", "Asia/Seoul");
        time.put("all_day", false);
        time.put("lunar", false);

        JSONObject description = new JSONObject();
        description.put("description", "테스트 중");

        MultiValueMap<String, Object> semi = new LinkedMultiValueMap<>();
        semi.add("title", title);
        semi.add("time", time.toString());
        semi.add("description", description);

        MultiValueMap<String, Object> parameter = new LinkedMultiValueMap<>();
        parameter.add("event", semi.toString());
        log.info(parameter.toString());
        kakaoCalender.createCalender(parameter);
    }
}
