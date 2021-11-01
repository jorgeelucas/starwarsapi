package com.starwarsapi.service.exception;

public class NaoPossuiItemException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NaoPossuiItemException(String msg) {
        super(msg);
    }
}
