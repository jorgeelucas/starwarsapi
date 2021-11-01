package com.starwarsapi.service.exception;

public class TraidorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public TraidorException(String msg) {
        super(msg);
    }
}
