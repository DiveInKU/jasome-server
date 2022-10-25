package com.diveinku.jasome.src.commons;

import com.diveinku.jasome.src.util.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAuthenticationDisabled = checkAnnotationExist(handler, NoAuth.class);
        if (isAuthenticationDisabled)
            return true;
        long memberIdFromJwt = jwtService.getMemberIdFromJwt();
        request.setAttribute("memberId", memberIdFromJwt);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean checkAnnotationExist(Object handler, Class<NoAuth> annotationClass) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(annotationClass) != null) {
            return true;
        }
        return false;
    }
}
