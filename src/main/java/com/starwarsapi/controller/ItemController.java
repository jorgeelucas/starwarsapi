package com.starwarsapi.controller;

import com.starwarsapi.dto.ItemDTO;
import com.starwarsapi.model.Item;
import com.starwarsapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> cadastrar(@RequestBody ItemDTO itemDto) {
        return ResponseEntity.ok(itemService.cadastrar(itemDto));
    }

    @GetMapping
    public ResponseEntity<List<Item>> obterTodosOsItens() {
        return ResponseEntity.ok(itemService.obterTodos());
    }

}
