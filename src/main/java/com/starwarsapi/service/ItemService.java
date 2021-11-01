package com.starwarsapi.service;

import com.starwarsapi.converter.Converter;
import com.starwarsapi.dto.ItemDTO;
import com.starwarsapi.model.Item;
import com.starwarsapi.repository.ItemRepository;
import com.starwarsapi.service.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item cadastrar(ItemDTO itemDto) {
        Item item = Converter.convert(itemDto, Item.class);

        item.setId(UUID.randomUUID().toString());
        return itemRepository.save(item);
    }

    public Item obterPorId(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Item " + id + " não encontrado"));
    }

    public List<Item> obterTodos() {
        return itemRepository.findAll();
    }
}
