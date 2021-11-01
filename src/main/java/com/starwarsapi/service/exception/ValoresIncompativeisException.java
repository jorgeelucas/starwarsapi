package com.starwarsapi.service.exception;

public class ValoresIncompativeisException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValoresIncompativeisException(String msg) {
        super(msg);
    }
}
