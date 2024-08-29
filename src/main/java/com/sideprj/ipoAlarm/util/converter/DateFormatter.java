package com.sideprj.ipoAlarm.util.converter;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
@Slf4j
public class DateFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter LOCAL_DATE_TINE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate format(String dateTime) {
        return dateTime != null ? parse(dateTime) : null;
    }

    public static String LocalDateTimeformat(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(LOCAL_DATE_TINE_FORMATTER) : null;
    }

    public static LocalDate parse(String dateTimeString) {
        if (dateTimeString != null) {
            try {
                return LocalDate.parse(dateTimeString, FORMATTER);
                // LocalDate를 java.sql.Date로 변환
            } catch (DateTimeParseException e) {
                // 날짜 파싱 오류 처리
                 // 또는 로그로 기록
                log.error(e.getMessage());
            }
        }
        return null;
    }

    public static Date convertDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(date != null){
            return format.parse(date);
        }else{
            return null;
        }
    }
}
