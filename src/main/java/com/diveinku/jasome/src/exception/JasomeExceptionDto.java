package com.diveinku.jasome.src.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JasomeExceptionDto {
    private String code;
    private String message;
}
