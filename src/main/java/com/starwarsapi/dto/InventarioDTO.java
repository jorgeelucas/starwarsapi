package com.starwarsapi.dto;

import com.starwarsapi.model.ItemDeInventario;
import lombok.Data;

import java.util.List;

@Data
public class InventarioDTO {
    private List<ItemDeInventario> itensDeInventario;
}
