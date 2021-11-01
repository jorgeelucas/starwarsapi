package com.starwarsapi.dto;

import com.starwarsapi.enums.Genero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RebeldeDTO {

    private String id;

    @NotBlank(message = "O campo nome é obrigatorio")
    private String nome;

    @NotNull(message = "O campo idade é obrigatorio")
    private Integer idade;

    @NotNull(message = "O campo genero é obrigatorio")
    private Genero genero;

    private Integer acusacoes = 0;

    @NotNull(message = "A localização do rebelde é obrigatorio")
    private LocalizacaoDTO localizacao;

    private InventarioDTO inventario;
}
