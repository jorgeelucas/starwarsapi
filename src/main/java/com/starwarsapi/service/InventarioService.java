package com.starwarsapi.service;

import com.starwarsapi.model.Inventario;
import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.repository.InventarioRepository;
import com.starwarsapi.service.exception.NaoPossuiItemException;
import com.starwarsapi.service.exception.QuantidadeInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public void addItem(Inventario inventario, List<ItemDeInventario> itensDeInventarios) {
        itensDeInventarios.stream().forEach(itemDeInventario1 -> {
            Optional<ItemDeInventario> itemDeInventarioOptional = inventario.getItensDeInventario().stream()
                    .filter(itemDeInventario -> itemDeInventario.getItem().getId().equals(itemDeInventario1.getItem().getId()))
                    .findAny();

            if (itemDeInventarioOptional.isPresent()) {
                ItemDeInventario itemDeInventario = itemDeInventarioOptional.get();
                itemDeInventario.setQuantidade(Integer.sum(itemDeInventario.getQuantidade(), itemDeInventario1.getQuantidade()));
            } else {
                ItemDeInventario itemDeInventario = new ItemDeInventario();
                itemDeInventario.setItem(itemDeInventario1.getItem());
                itemDeInventario.setQuantidade(itemDeInventario1.getQuantidade());
                inventario.getItensDeInventario().add(itemDeInventario);
            }
        });
    }

    public void removerItem(Inventario inventario, List<ItemDeInventario> itensDeInventarios) {
        itensDeInventarios.stream().forEach(itemDeInventario1 -> {
            Optional<ItemDeInventario> itemDeInventarioOptional = inventario.getItensDeInventario().stream()
                    .filter(itemDeInventario -> itemDeInventario.getItem().getId().equals(itemDeInventario1.getItem().getId()))
                    .findAny();

            if (itemDeInventarioOptional.isPresent()) {
                ItemDeInventario itemDeInventario = itemDeInventarioOptional.get();
                if (itemDeInventario.getQuantidade() < itemDeInventario1.getQuantidade())
                    throw new QuantidadeInsuficienteException("Quantidade insuficiente de item");
                itemDeInventario.setQuantidade(itemDeInventario.getQuantidade() - itemDeInventario1.getQuantidade());
            } else {
                throw new NaoPossuiItemException("Nao possui item");
            }
        });
    }
}
