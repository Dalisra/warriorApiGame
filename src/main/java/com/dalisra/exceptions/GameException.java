package com.dalisra.exceptions;

import io.micronaut.http.HttpStatus;

public class GameException extends RuntimeException{

    private final HttpStatus httpStatus;

    public GameException(String message, Integer statusCode) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(statusCode);
    }

    public GameException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public GameException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public GameException(HttpStatus status) {
        super(status.getReason());
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
