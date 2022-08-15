package com.diveinku.jasome.src.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception{
    private BaseResponseStatus status;

    @Override
    public String getMessage() {
        return status.getMessage();
    }
}
