package com.semihbkgr.Corbeau.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus httpStatus;
    private Map<String,Object> errorAttributes;

}
