package com.diveinku.jasome.src.exception.common;

import com.diveinku.jasome.src.exception.ExceptionCodeAndMessage;
import lombok.Getter;

@Getter
public class JasomeException extends RuntimeException {
    private final ExceptionCodeAndMessage codeAndMessage =
            ExceptionCodeAndMessage.findByClass(this.getClass());

    private int code;
    private String message;

    public JasomeException(){
        this.code = codeAndMessage.getCode();
        this.message = codeAndMessage.getMessage();
    }
}
