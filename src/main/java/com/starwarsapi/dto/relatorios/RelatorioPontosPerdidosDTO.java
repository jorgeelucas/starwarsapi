package com.starwarsapi.dto.relatorios;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioPontosPerdidosDTO {
    private Integer total_de_pontos_perdidos;
}
