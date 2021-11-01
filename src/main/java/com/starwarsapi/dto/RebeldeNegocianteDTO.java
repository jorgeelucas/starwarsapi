package com.starwarsapi.dto;

import com.starwarsapi.model.ItemDeInventario;
import lombok.Data;

import java.util.List;

@Data
public class RebeldeNegocianteDTO {
    private String idRebelde;
    private List<ItemDeInventario> itensDeInventario;
}
