package com.diveinku.jasome.src.commons;

public class ValidationMessage {
    private ValidationMessage() {}

    //성공
    public static final int SUCCESS_CODE = 1000;
    public static final String SUCCESS_MESSAGE = "요청 성공했습니다.";

    //메세지
    public static final String EMPTY_FIELD = "빈 항목을 입력해주세요.";
    public static final String INVALID_EMAIL = "이메일 형식을 확인해주세요.";

    public static final String INVALID_PASSWORD = "비밀번호는 6자 이상, 20자 이상의 영어와 숫자가 포함된 문자열입니다.";

    public static final String INVALID_NAME = "이름은 1자 이상, 20자 이하의 문자열입니다.";
    //형식
    /*
    (?=.*[a-zA-Z]): 영문
    (?=.*?[A-Z]): 최소 한개의 대문자 영문
    (?=.*?[0-9]): 최소 한개의 숫자
    */
    public static final String MEMBER_PASSWORD_FORMAT = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$";
    public static final String MEMBER_NAME_FORMAT = "^(?=.).{2,20}$";
}
