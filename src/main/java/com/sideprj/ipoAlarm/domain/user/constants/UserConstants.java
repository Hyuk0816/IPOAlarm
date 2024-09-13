package com.sideprj.ipoAlarm.domain.user.constants;

public class UserConstants {

    private UserConstants() {

    }

    public static final String  STATUS_201 = "201";
    public static final String  MESSAGE_201 = "User created successfully";
    public static final String  STATUS_200 = "200";
    public static final String  MESSAGE_200 = "Request processed successfully";
    public static final String  STATUS_417 = "417";
    public static final String  MESSAGE_417_UPDATE= "Update operation failed. Please try again or contact Dev team";
    public static final String  MESSAGE_417_DELETE= "Delete operation failed. Please try again or contact Dev team";

    public static final String  STATUS_404 = "404";
    public static final String  MESSAGE_404 = "User Not Found";

    public static final String MESSAGE_ALREADY_EXISTS = "User already exists";

    public static final String REQUIRED_LOGIN = "로그인이 필요한 서비스 입니다.";
}
