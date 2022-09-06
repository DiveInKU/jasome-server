package com.diveinku.jasome.src.exception;

import lombok.Getter;

@Getter
public class JasomeException extends RuntimeException {
    private final ExceptionCodeAndMessage codeAndMessage =
            ExceptionCodeAndMessage.findByClass(this.getClass());

    private String code;
    private String message;

    public JasomeException(){
        this.code = codeAndMessage.getCode();
        this.message = codeAndMessage.getMessage();
    }
}
