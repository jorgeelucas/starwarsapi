package com.starwarsapi.dto;

import lombok.Data;

@Data
public class ItemDeInventarioDTO {
    private ItemDTO item;

    private Integer quantidade;
}
