package com.starwarsapi.controller;

import com.starwarsapi.dto.ItemDeInventarioDTO;
import com.starwarsapi.dto.LocalizacaoDTO;
import com.starwarsapi.dto.NegociacaoItemDTO;
import com.starwarsapi.dto.RebeldeDTO;
import com.starwarsapi.model.Rebelde;
import com.starwarsapi.service.RebeldeService;
import com.starwarsapi.service.exception.ObjetoNaoEncontradoException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Rebeldes")
@RestController
@RequestMapping("/api/v1/rebeldes")
public class RebeldeController {

    @Autowired
    private RebeldeService rebeldeService;

    @PostMapping
    @ApiOperation(value = "Cadastrar novo rebelde")
    public ResponseEntity<Rebelde> adicionar(@RequestBody @Valid RebeldeDTO rebeldeDto) {
//        if (rebeldeDto.getNome() == null)
//            throw new ObjetoNaoEncontradoException("erro");
        Rebelde novoRebelde = rebeldeService.adicionarRebelde(rebeldeDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoRebelde.getId()).toUri();
        return ResponseEntity.created(uri).body(novoRebelde);
    }

    @GetMapping
    @ApiOperation(value = "Recuperar todos os rebeldes salvos")
    public ResponseEntity<List<Rebelde>> obterTodos() {
        return ResponseEntity.ok(rebeldeService.buscarTodos());
    }

    @PostMapping("/{idRebelde}")
    @ApiOperation(value = "Adicionar o item ao rebelde")
    public ResponseEntity<Rebelde> adicionarItem(@PathVariable String idRebelde, @RequestBody ItemDeInventarioDTO itemDeInventarioDto) {
        return ResponseEntity.ok(rebeldeService.addItem(idRebelde, itemDeInventarioDto));
    }

    @PutMapping("localizacao/{idRebelde}")
    @ApiOperation(value = "Atualizar a localização de um determinado rebelde")
    public ResponseEntity<Rebelde> atualizarLocalizacaoRebelde(@PathVariable String idRebelde,
                                                               @RequestBody LocalizacaoDTO localizacaoDto) {
        return ResponseEntity.ok(rebeldeService.atualizarLocalizacaoRebelde(idRebelde, localizacaoDto));
    }

    @PatchMapping("/acusar-traidor")
    @ApiOperation(value = "Acusar um determinado rebelde de traidor")
    public ResponseEntity<Void> acusarTraidor(@RequestParam(value = "traidor") String traidor) {
        rebeldeService.acusarTraidor(traidor);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/negociar")
    @ApiOperation(value = "Negociar itens do inventário")
    public ResponseEntity<Void> negociar(@RequestBody NegociacaoItemDTO negociacaoItemDTO) {
        rebeldeService.negociacao(negociacaoItemDTO);
        return ResponseEntity.ok().build();
    }
}
