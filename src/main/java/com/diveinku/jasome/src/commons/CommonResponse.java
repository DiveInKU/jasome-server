package com.diveinku.jasome.src.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.diveinku.jasome.src.commons.ValidationMessage.SUCCESS_CODE;
import static com.diveinku.jasome.src.commons.ValidationMessage.SUCCESS_MESSAGE;

@AllArgsConstructor
@Getter
// null인 데이터는 json 결과에 포함하지 않음
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private int code;
    private String message;
    private T result;

    public CommonResponse() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    public CommonResponse(T data) {
        this();
        this.result = data;
    }

    public static <T> CommonResponse<T> from(T data) {
        return new CommonResponse<>(data);
    }
}
