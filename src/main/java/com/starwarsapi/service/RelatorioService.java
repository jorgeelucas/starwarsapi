package com.starwarsapi.service;

import com.starwarsapi.dto.relatorios.RelatorioItemMediaDTO;
import com.starwarsapi.dto.relatorios.RelatorioMediaDeRecursosPorRebelde;
import com.starwarsapi.dto.relatorios.RelatorioPontosPerdidosDTO;
import com.starwarsapi.dto.relatorios.RelatorioPorcentagemDeRebeldesDTO;
import com.starwarsapi.model.Item;
import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.model.Rebelde;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RelatorioService {

    @Autowired
    private RebeldeService rebeldeService;

    public RelatorioPorcentagemDeRebeldesDTO porcentagemDeTraidores() {
        List<Rebelde> rebeldes = rebeldeService.buscarTodos();
        Double media = rebeldes.stream()
                .flatMapToInt(rebelde -> IntStream.of(rebelde.getAcusacoes() > 2 ? 1 : 0))
                .average()
                .getAsDouble();

        return RelatorioPorcentagemDeRebeldesDTO.builder()
                .quantidade_de_rebeldes(rebeldes.size())
                .traidores(String.format("%,.0f%%", media * 100))
                .build();
    }

    public void porcentagemDeRebeldes() {
    }


    public List<RelatorioMediaDeRecursosPorRebelde> quantidadeMediaDeRecursosPorRebelde() {
        List<Rebelde> todosRebeldes = rebeldeService.buscarTodos();

        List<ItemDeInventario> collect = todosRebeldes.stream()
                .map(Rebelde::getInventario)
                .flatMap(inventario -> inventario.getItensDeInventario().stream())
                .collect(Collectors.toList());


        Map<String, ItemQuantidadeDTO> contador = new HashMap<>();

        collect.stream()
                .forEach(itemDeInventario -> {
                    Item item = itemDeInventario.getItem();
                    Integer quantidade = itemDeInventario.getQuantidade();

                    contador.computeIfPresent(item.getId(), (key, value) -> {

                        value.setQuantidades(IntStream.concat(contador.get(key).getQuantidades(), IntStream.of(quantidade)));
                        return value;
                    });

                    contador.computeIfAbsent(item.getId(), value -> {
                        ItemQuantidadeDTO itemQuantidadeDTO = new ItemQuantidadeDTO();
                        itemQuantidadeDTO.setQuantidades(IntStream.of(quantidade));
                        itemQuantidadeDTO.setItem(item);
                        return itemQuantidadeDTO;
                    });
                });

        return contador.keySet().stream()
                .map(key -> {
                    ItemQuantidadeDTO itemQuantidadeDTO = contador.get(key);
                    return RelatorioMediaDeRecursosPorRebelde.builder()
                            .media_por_recurso(RelatorioItemMediaDTO.builder()
                                    .item_name(itemQuantidadeDTO.getItem().getNome())
                                    .media(itemQuantidadeDTO.quantidades.average().getAsDouble())
                                    .build())
                            .build();
                }).collect(Collectors.toList());
    }

    public RelatorioPontosPerdidosDTO pontosPerdidosPorTraidores() {
        List<Rebelde> rebeldes = rebeldeService.buscarPorQuantidadeDeAcusacoesIgualOuMaiorQue(3);

        Integer totalDePontos = rebeldes.stream()
                .map(rebelde -> rebelde.getInventario())
                .flatMap(inventario -> inventario.getItensDeInventario().stream())
                .map(itemDeInventario -> itemDeInventario.getItem().getPontos() * itemDeInventario.getQuantidade())
                .reduce(0, (acumulador, elemento) -> acumulador + elemento);

        return RelatorioPontosPerdidosDTO
                .builder()
                .total_de_pontos_perdidos(totalDePontos)
                .build();
    }

    @Getter
    @Setter
    public class ItemQuantidadeDTO {
        private Item item;
        private IntStream quantidades;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemQuantidadeDTO that = (ItemQuantidadeDTO) o;
            if (that.item == null) return false;
            return Objects.equals(item.getId(), that.item.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(item);
        }
    }
}
