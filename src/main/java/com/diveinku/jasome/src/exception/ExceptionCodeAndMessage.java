package com.diveinku.jasome.src.exception;

import com.diveinku.jasome.src.exception.common.*;
import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionCodeAndMessage {

    // 잘못된 에러 (Exception과 대응되는 메세지를 정의하지 않았을 경우)
    NOT_FOUND_DEFINED_EXCEPTION(1001, "발생한 에러의 에러 코드가 정의되지 않았습니다.", NotFoundDefinedException.class),
    NOT_FOUND(1002, "잘못된 API 경로입니다.", NoHandlerFoundException.class),

    // 인증 인가
    INVALID_TOKEN(1003, "Access token 값을 확인해주세요.", InvalidTokenException.class),
    AUTH_DENIED(1004, "해당하는 유저의 권한으로 접근할 수 없는 요청입니다.", AuthDeniedException.class),

    // 멤버
    DUPLICATE_EMAIL(2001, "중복된 이메일입니다.",DuplicateEmailException.class);

    private final int code;
    private final String message;
    private final Class<? extends Exception> type;
    public static ExceptionCodeAndMessage findByClass(Class<? extends JasomeException> type) {
        return Arrays.stream(ExceptionCodeAndMessage.values())
                .filter(e -> e.getType().equals(type))
                .findAny()
                .orElseThrow(NotFoundDefinedException::new);
    }
}
