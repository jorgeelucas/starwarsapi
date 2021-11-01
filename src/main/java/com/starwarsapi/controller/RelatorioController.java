package com.starwarsapi.controller;

import com.starwarsapi.dto.relatorios.RelatorioMediaDeRecursosPorRebelde;
import com.starwarsapi.dto.relatorios.RelatorioPontosPerdidosDTO;
import com.starwarsapi.dto.relatorios.RelatorioPorcentagemDeRebeldesDTO;
import com.starwarsapi.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/porcentagem-traidores")
    public ResponseEntity<RelatorioPorcentagemDeRebeldesDTO> porcentagemDeTraidores() {
        return ResponseEntity.ok(relatorioService.porcentagemDeTraidores());
    }

    // TODO
    public ResponseEntity<Void> porcentagemDeRebeldes() {
        relatorioService.porcentagemDeRebeldes();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/quantidade-recursos-por-rebelde")
    public ResponseEntity<List<RelatorioMediaDeRecursosPorRebelde>> quantidadeMediaRecursosPorRebelde() {
        return ResponseEntity.ok(relatorioService.quantidadeMediaDeRecursosPorRebelde());
    }

    @GetMapping("/pontos-perdidos")
    public ResponseEntity<RelatorioPontosPerdidosDTO> pontosPerdidosPorTraidores() {
        return ResponseEntity.ok(relatorioService.pontosPerdidosPorTraidores());
    }

}
