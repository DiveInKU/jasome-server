package com.diveinku.jasome.src.commons;

public class ValidationMessage {
    private ValidationMessage() {}

    //성공
    public static final int SUCCESS_CODE = 1000;
    public static final String SUCCESS_MESSAGE = "요청 성공했습니다.";

    //형식
    /*
    (?=.*[a-zA-Z]): 영문
    (?=.*?[A-Z]): 최소 한개의 대문자 영문
    (?=.*?[0-9]): 최소 한개의 숫자
    */
    public static final String MEMBER_PASSWORD_FORMAT = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$";
    public static final String MEMBER_NAME_FORMAT = "^(?=.).{2,20}$";
}
