package com.semihbkgr.corbeau.error.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebExcahngeBindExceptionResponse {

    private int httpStatusCode;

    private String httpStats;

    private List<FiledErrorResponse> fieldErrors;

}
