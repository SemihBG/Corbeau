package com.semihbkgr.corbeau.error.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebExcahngeBindExceptionResponse {

    private int httpStatusCode;

    private String httpStats;

    private List<FiledErrorResponse> fieldErrors;

}
