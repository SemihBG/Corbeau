package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.error.ArtifactException;
import com.semihbkgr.corbeau.error.RequestTraceException;
import com.semihbkgr.corbeau.error.response.ArtifactExceptionResponse;
import com.semihbkgr.corbeau.error.response.FiledErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(WebExchangeBindException e, Model model, ServerRequest request) {
        var fieldErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> FiledErrorResponse.builder()
                        .fieldName(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        model.addAttribute("fieldErrors", fieldErrorList);
        model.addAttribute("httpStatusCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("httpStatus", "BAD REQUEST");
        model.addAttribute("handlerMessage", "Invalid parameter error");
        return "moderation/error";
    }

    @ExceptionHandler(ArtifactException.class)
    @ResponseBody
    public ArtifactExceptionResponse handleArtifactException(ArtifactException e, Model model) {
        return ArtifactExceptionResponse.wrap(e);
    }

    @ExceptionHandler(RequestTraceException.class)
    @ResponseBody
    public Exception asd(RequestTraceException exception){
        return exception;
    }

}
