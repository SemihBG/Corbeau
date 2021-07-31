package com.semihbkgr.corbeau.error.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiledErrorResponse {

    private String fieldName;

    private Object rejectedValue;

    private String message;

}
