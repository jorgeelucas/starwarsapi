package com.starwarsapi.dto.relatorios;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioPorcentagemDeRebeldesDTO {
    private Integer quantidade_de_rebeldes;
    private String traidores;
}
