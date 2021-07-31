package com.semihbkgr.corbeau.error;

public class ArtifactExcepiton extends RuntimeException{

    public ArtifactExcepiton() {
    }

    public ArtifactExcepiton(String message) {
        super(message);
    }

    public ArtifactExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtifactExcepiton(Throwable cause) {
        super(cause);
    }

    public ArtifactExcepiton(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
