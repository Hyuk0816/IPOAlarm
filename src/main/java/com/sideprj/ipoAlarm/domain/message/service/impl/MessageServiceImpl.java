package com.sideprj.ipoAlarm.domain.message.service.impl;

import com.sideprj.ipoAlarm.domain.message.client.KakaoMessage;
import com.sideprj.ipoAlarm.domain.message.dto.MessageDto;
import com.sideprj.ipoAlarm.domain.message.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final RedisTemplate<String ,String> redisTemplate;
    private final KakaoMessage kakaoMessage;

    @Override
    @Scheduled(cron = "0 13 23 * * ?")
    public void messageSetting() {
        String accessToken = redisTemplate.opsForValue().get("rlawogur816@naver.com-kakao_access");
        log.info("access Token : " + accessToken);
        MessageDto msg = new MessageDto();
        msg.setMessage("테스트입니다.");
        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", "text");
        templateObj.put("text", msg.getMessage());
        templateObj.put("link", "test link");
        templateObj.put("button_title", "test btn");

        log.info("요청 보내기 시작!");
        String content = "application/x-www-form-urlencoded";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.set("Content-Type", content);
        kakaoMessage.sendMessage(headers, templateObj);
        log.info("메세지 보내기 요청!~!");
    }
}
