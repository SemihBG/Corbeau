package com.semihbkgr.corbeau.error.response;

import com.semihbkgr.corbeau.error.ArtifactException;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtifactExceptionResponse {

    private int httpStatusCode;

    private HttpStatus httpStatus;

    private String message;

    public static ArtifactExceptionResponse wrap(@NonNull ArtifactException e) {
        return builder()
                .httpStatusCode(e.getRawStatusCode())
                .httpStatus(e.getStatusCode())
                .message(e.getMessage())
                .build();
    }

}
