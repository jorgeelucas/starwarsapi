package com.starwarsapi.dto.relatorios;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioMediaDeRecursosPorRebelde {
    private RelatorioItemMediaDTO media_por_recurso;
}
