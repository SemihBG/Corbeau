package com.semihbkgr.Corbeau.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public SimpleAccessDeniedHandler() {
        objectMapper=new ObjectMapper();
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException{
        String responseJson=objectMapper.writeValueAsString(
                new AccessDeniedException("Access Denied",e));
        httpServletResponse.getWriter().print(responseJson);
    }

    @Data
    public static class AccessDeniedExceptionResponse {

        private long timestamp;
        private HttpStatus httpStatus;
        private Class<? extends Throwable> exception;
        private String exceptionMessage;
        private String message;

        public AccessDeniedExceptionResponse(String message,AccessDeniedException ex) {
            this.message = message;
            exception =ex.getClass();
            exceptionMessage=ex.getMessage();
            timestamp=System.currentTimeMillis();
            httpStatus=HttpStatus.UNAUTHORIZED;
        }

    }


}
