package com.starwarsapi.model;

import com.starwarsapi.enums.Genero;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Rebelde {

    @Id
    private String id;
    private String nome;
    private Integer idade;
    private Genero genero;
    private Integer acusacoes = 0;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Localizacao localizacao;

    @OneToOne(cascade = CascadeType.ALL)
    private Inventario inventario;

}
