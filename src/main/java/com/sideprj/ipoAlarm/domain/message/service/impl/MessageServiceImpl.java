package com.sideprj.ipoAlarm.domain.message.service.impl;

import com.sideprj.ipoAlarm.domain.message.client.KakaoMessage;
import com.sideprj.ipoAlarm.domain.message.dto.MessageDto;
import com.sideprj.ipoAlarm.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final KakaoMessage kakaoMessage;

    @Override
  //  @Scheduled(cron = "0 51 9 * * ?")
    public void messageSetting() {

        MessageDto msg = new MessageDto();
        msg.setMessage("테스트입니다.");
        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", "text");
        templateObj.put("text", msg.getMessage());
        templateObj.put("link", "www.naver.com");
        templateObj.put("button_title", "test btn");


        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("template_object", templateObj.toString());

        log.info("요청 보내기 시작!");
        log.info(parameters.toString());
        kakaoMessage.sendMessage(parameters);
        log.info("메세지 보내기 요청!~!");
    }
}
