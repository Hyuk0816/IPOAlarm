package com.sideprj.ipoAlarm.domain.calender.service;

import com.sideprj.ipoAlarm.domain.calender.client.KakaoCalender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoCalenderService {

    private final KakaoCalender kakaoCalender;
    private final RedisTemplate<String, String> redisTemplate;

    public void calenderSetting(String accessToken) {
        // 이벤트 데이터 생성
        JSONObject event = new JSONObject();

        // 제목 추가
        event.put("title", "이벤트테스트");

        // 시간 정보 추가
        JSONObject time = new JSONObject();
        time.put("start_at", "2024-08-02T03:00:00Z");
        time.put("end_at", "2024-08-03T03:00:00Z");
        time.put("time_zone", "Asia/Seoul");
        time.put("all_day", false);
        time.put("lunar", false);

        // 시간 정보 추가
        event.put("time", time);

        // 설명 추가
        JSONObject description = new JSONObject();
        description.put("description", "테스트 중");
        event.put("description", description);

        // MultiValueMap 생성
        MultiValueMap<String, Object> parameter = new LinkedMultiValueMap<>();
        parameter.add("event", event.toString()); // JSON 문자열로 변환하여 추가

        log.info(parameter.toString());

        // 액세스 토큰 가져오기


        // Kakao Calendar API 호출
        kakaoCalender.createCalender(accessToken, parameter);
    }

}
