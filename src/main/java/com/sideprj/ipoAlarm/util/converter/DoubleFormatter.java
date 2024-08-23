package com.sideprj.ipoAlarm.util.converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoubleFormatter {

    public static Double parseDouble(String value) {
        if (value != null && !value.isEmpty()) {
            // "-" 또는 숫자인 경우를 처리
            try {
                return Double.valueOf(value.replace(",", "").trim());
            } catch (NumberFormatException e) {
                // 오류 처리: 로그 기록
                log.error(e.getMessage());
            }
        }
        return null; // 변환 실패 시 null 반환


    }
}