package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.error.ArtifactException;
import com.semihbkgr.corbeau.error.response.ArtifactExceptionResponse;
import com.semihbkgr.corbeau.error.response.FiledErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler(WebExchangeBindException.class)
    public String handleValidationException(WebExchangeBindException e, Model model) {
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
        model.addAttribute("handlerMessage","Invalid parameter error");
        return "moderation/error";
    }

    @ExceptionHandler(ArtifactException.class)
    @ResponseBody
    public ArtifactExceptionResponse handleArtifactException(ArtifactException e, Model model){
        return ArtifactExceptionResponse.wrap(e);
    }

}
