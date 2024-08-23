package com.sideprj.ipoAlarm.util.converter;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
@Slf4j
public class DateFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Date format(String dateTime) {
        return dateTime != null ? parse(dateTime) : null;
    }

    public static Date parse(String dateTimeString) {
        if (dateTimeString != null) {
            try {
                LocalDate localDate = LocalDate.parse(dateTimeString, FORMATTER);
                return java.sql.Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
            } catch (DateTimeParseException e) {
                // 날짜 파싱 오류 처리
                 // 또는 로그로 기록
                log.error(e.getMessage());
            }
        }
        return null;
    }
}
