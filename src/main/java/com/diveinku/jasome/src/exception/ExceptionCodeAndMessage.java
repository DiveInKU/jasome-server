package com.diveinku.jasome.src.exception;

import com.diveinku.jasome.src.exception.common.*;
import com.diveinku.jasome.src.exception.interview.FileConvertException;
import com.diveinku.jasome.src.exception.member.*;
import com.diveinku.jasome.src.exception.resume.NonExistentResumeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionCodeAndMessage {

    // 잘못된 에러 (Exception과 대응되는 메세지를 정의하지 않았을 경우)
    NOT_FOUND_DEFINED_EXCEPTION(1001, "발생한 에러의 에러 코드가 정의되지 않았습니다.", NotFoundDefinedException.class),
    NOT_FOUND(1002, "잘못된 API 경로입니다.", NoHandlerFoundException.class),
    AUTH_DENIED(1003, "해당하는 유저의 권한으로 접근할 수 없는 요청입니다.", AuthDeniedException.class),

    // 인증 인가
    INVALID_JWT(1010, "JWT 값을 확인해주세요.", InvalidJwtException.class),
    EMPTY_JWT(1011, "JWT 토큰을 입력해주세요.", EmptyJwtException.class),
    EXPIRED_JWT(1012, "JWT 토큰이 만료되었습니다.", ExpiredJwtException.class),
    // Valid 체크
    COMMON_INVALID_FIELD(1100, "항목을 확인해주세요.", MethodArgumentNotValidException.class),
    COMMON_UNMATCHED_REGEX(1101, "항목이 형식과 맞지 않습니다.", MethodArgumentNotValidException.class),
    EMPTY_FIELD(1110, "빈 항목을 입력해주세요.", MethodArgumentNotValidException.class),
    INVALID_EMAIL(1111, "이메일 형식을 확인해주세요.", MethodArgumentNotValidException.class),
    INVALID_PASSWORD(1112, "비밀번호는 6자 이상, 20자 이상의 영어와 숫자가 포함된 문자열입니다.", MethodArgumentNotValidException.class),
    INVALID_NAME(1113, "이름은 1자 이상, 20자 이하의 문자열입니다.", MethodArgumentNotValidException.class),

    // 멤버
    DUPLICATE_EMAIL(2001, "중복된 이메일입니다.", DuplicateEmailException.class),
    EMAIL_NOT_EXISTS(2002, "해당하는 이메일의 유저가 없습니다.", NonExistentEmailException.class),
    INCORRECT_PASSWORD(2003, "비밀번호가 일치하지 않습니다.", IncorrectPasswordException.class),
    INCORRECT_ID(2004, "존재하지 않는 유저입니다.", NonExistentMemberException.class),

    // 자기소개서
    INCORRECT_RESUME_ID(3001, "존재하지 않는 자기소개서입니다.", NonExistentResumeException.class),

    // 인터뷰
    FILE_CONVERT_FAIL(4001, "파일 변환에 실패했습니다", FileConvertException.class),
    ;

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
