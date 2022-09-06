package com.diveinku.jasome.src.security;

import com.diveinku.jasome.src.exception.ExceptionCodeAndMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 유효하지 않은 JWT, 혹은 유저, 401 에러
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 에러
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{"
                    + "\"code\":\"" + ExceptionCodeAndMessage.INVALID_TOKEN.getCode() +"\","
                    + "\"message\":\"" + ExceptionCodeAndMessage.INVALID_TOKEN.getMessage() + "\"}");

            response.getWriter().flush();
    }
}
