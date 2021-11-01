package com.starwarsapi.service.exception;

public class QuantidadeInsuficienteException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public QuantidadeInsuficienteException(String msg) {
        super(msg);
    }
}
