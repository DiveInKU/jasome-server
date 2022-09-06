package com.diveinku.jasome.src.security;

import com.diveinku.jasome.src.exception.ExceptionCodeAndMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write( "{"
                + "\"code\":\"" + ExceptionCodeAndMessage.AUTH_DENIED.getCode() + "\","
                + "\"message\":\"" + ExceptionCodeAndMessage.AUTH_DENIED.getMessage() + "\"}");
        response.getWriter().flush();
    }
}
