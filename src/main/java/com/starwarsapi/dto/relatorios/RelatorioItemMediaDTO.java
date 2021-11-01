package com.starwarsapi.dto.relatorios;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioItemMediaDTO {
    private String item_name;
    private Double media;
}
