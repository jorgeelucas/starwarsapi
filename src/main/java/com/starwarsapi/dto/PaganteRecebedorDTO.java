package com.starwarsapi.dto;

import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.model.Rebelde;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaganteRecebedorDTO {
    private Rebelde pagante;
    private Rebelde recebedor;
    private List<ItemDeInventario> itensDeInventario;
}
