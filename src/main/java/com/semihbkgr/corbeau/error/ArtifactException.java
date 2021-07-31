package com.semihbkgr.corbeau.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class ArtifactException extends HttpStatusCodeException {


    public ArtifactException(HttpStatus statusCode) {
        super(statusCode);
    }

    public ArtifactException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    protected ArtifactException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    protected ArtifactException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }

    protected ArtifactException(String message, HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }

}
