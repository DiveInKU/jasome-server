package com.diveinku.jasome.src.exception;

import com.diveinku.jasome.src.exception.member.DuplicateEmailException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionCodeAndMessage {

    // 잘못된 에러 (Exception과 대응되는 메세지를 정의하지 않았을 경우)
    NOT_FOUND_DEFINED_EXCEPTION("0001", "발생한 에러의 에러 코드가 정의되지 않았습니다.", NotFoundDefinedException.class),
    NOT_FOUND("0002", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),

    // 인증 인가
    INVALID_TOKEN("0002", "Access token 값을 확인해주세요.", InvalidTokenException.class),
    AUTH_DENIED("0003", "해당하는 유저의 권한으로 접근할 수 없는 요청입니다.", AuthDeniedException.class),

    // 멤버
    DUPLICATE_EMAIL("1000", "중복된 이메일입니다.",DuplicateEmailException.class);

    private final String code;
    private final String message;
    private final Class<? extends Exception> type;
    public static ExceptionCodeAndMessage findByClass(Class<? extends JasomeException> type) {
        return Arrays.stream(ExceptionCodeAndMessage.values())
                .filter(e -> e.getType().equals(type))
                .findAny()
                .orElseThrow(NotFoundDefinedException::new);
    }
}
