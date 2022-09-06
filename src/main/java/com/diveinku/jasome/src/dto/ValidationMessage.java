package com.diveinku.jasome.src.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ValidationMessage {
    private ValidationMessage() {

    }
    public static final String EMPTY_MESSAGE = "항목이 비어있습니다.";
    public static final String INVALID_EMAIL = "이메일 형식을 확인해주세요.";

}
