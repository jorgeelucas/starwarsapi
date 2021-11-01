package com.starwarsapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionPadrao {
    private Integer status;
    private String mensagem;
    private Long timestamp;
}
