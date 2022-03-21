package com.aurindo.posterr.domain.exception;

public class PosterrException extends RuntimeException {

    public PosterrException() {
        super();
    }

    public PosterrException(String message) {
        super(message);
    }

    public PosterrException(String message, Throwable t) {
        super(message, t);
    }

}
