package com.starwarsapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampoMensagem {
    private String campo;
    private String mensagem;
}
