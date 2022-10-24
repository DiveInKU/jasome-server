package com.diveinku.jasome.src.exception.common;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.exception.ExceptionCodeAndMessage;
import com.diveinku.jasome.src.exception.common.JasomeException;
import com.diveinku.jasome.src.exception.common.JasomeExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.diveinku.jasome.src.exception.ExceptionCodeAndMessage.*;

// 예외가 발생했을 때 json 형태로 반환할 때 사용하는 어노테이션
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
                .body(new JasomeExceptionDto(NOT_FOUND.getCode(), NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JasomeExceptionDto> validExceptionHandler(MethodArgumentNotValidException exception){
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(findError(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> validExceptionHandlerForParameter(ConstraintViolationException exception){
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(findError(exception));
    }

    private JasomeExceptionDto findError(ConstraintViolationException e){
        Optional<ConstraintViolation<?>> constraintViolation = e.getConstraintViolations().stream().findAny();
        String detail = null;
        if(constraintViolation.isPresent()){
            String annotation = constraintViolation.get().getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            detail = constraintViolation.get().getMessage();
            String fieldName = null;
            for (Path.Node node : constraintViolation.get().getPropertyPath()) {
                fieldName = node.getName();
            }
            return getInvalidInputResponse(annotation, fieldName, detail);

        }
        return new JasomeExceptionDto(COMMON_INVALID_FIELD.getCode(), COMMON_INVALID_FIELD.getMessage(), detail);
    }

    private JasomeExceptionDto findError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String detail = null;
        if (bindingResult.hasErrors()){
            String code = bindingResult.getFieldError().getCode();
            String field = bindingResult.getFieldError().getField();
            detail = bindingResult.getFieldError().getDefaultMessage();
            return getInvalidInputResponse(code, field, detail);
        }
        return new JasomeExceptionDto(COMMON_INVALID_FIELD.getCode(), COMMON_INVALID_FIELD.getMessage(), detail);
    }

    private JasomeExceptionDto getInvalidInputResponse(String annotation, String field, String detail){
        switch(annotation){
            case "NotBlank":
                return new JasomeExceptionDto(EMPTY_FIELD.getCode(), EMPTY_FIELD.getMessage(), detail);
            case "Email":
                return new JasomeExceptionDto(INVALID_EMAIL.getCode(), INVALID_EMAIL.getMessage(), detail);
            case "Pattern":
                // 에러가 발생한 필드 이름
                switch(field){
                    case "name":
                        return new JasomeExceptionDto(INVALID_NAME.getCode(), INVALID_NAME.getMessage(), detail);
                    case "password":
                        return new JasomeExceptionDto(INVALID_PASSWORD.getCode(), INVALID_PASSWORD.getMessage(), detail);
                }
                return new JasomeExceptionDto(COMMON_UNMATCHED_REGEX.getCode(), COMMON_UNMATCHED_REGEX.getMessage(), detail);
        }
        return new JasomeExceptionDto(COMMON_INVALID_FIELD.getCode(), COMMON_INVALID_FIELD.getMessage(), detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unhandledExceptionHandler(final Exception exception) {
        log.info(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().build();
    }
}
