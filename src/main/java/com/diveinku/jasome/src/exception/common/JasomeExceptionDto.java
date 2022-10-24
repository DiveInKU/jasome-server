package com.diveinku.jasome.src.exception.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.diveinku.jasome.src.exception.ExceptionCodeAndMessage.COMMON_INVALID_FIELD;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JasomeExceptionDto {
    private int code;
    private String message;
    private String detail;

    public JasomeExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
