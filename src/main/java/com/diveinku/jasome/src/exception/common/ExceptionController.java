package com.diveinku.jasome.src.exception.common;

import com.diveinku.jasome.src.exception.ExceptionCodeAndMessage;
import com.diveinku.jasome.src.exception.common.JasomeException;
import com.diveinku.jasome.src.exception.common.JasomeExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(JasomeException.class)
    public ResponseEntity<JasomeExceptionDto> jasomeExceptionHandler(final JasomeException exception){
        log.info(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new JasomeExceptionDto(exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<JasomeExceptionDto> notFoundExceptionHandler(final NoHandlerFoundException exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new JasomeExceptionDto(ExceptionCodeAndMessage.NOT_FOUND.getCode(), ExceptionCodeAndMessage.NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unhandledExceptionHandler(final Exception exception) {
        log.info(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().build();
    }
}
