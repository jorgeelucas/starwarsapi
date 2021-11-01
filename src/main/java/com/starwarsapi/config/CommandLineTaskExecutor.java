package com.starwarsapi.config;

import com.starwarsapi.enums.Genero;
import com.starwarsapi.model.Inventario;
import com.starwarsapi.model.Item;
import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.model.Localizacao;
import com.starwarsapi.model.Rebelde;
import com.starwarsapi.repository.ItemRepository;
import com.starwarsapi.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class CommandLineTaskExecutor implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Override
    public void run(String... args) throws Exception {

        Item arma = new Item();
        arma.setNome("Arma");
        arma.setPontos(4);

        Item municao = new Item();
        municao.setNome("Munição");
        municao.setPontos(3);

        Item agua = new Item();
        agua.setNome("Agua");
        agua.setPontos(2);

        Item comida = new Item();
        comida.setNome("Comida");
        comida.setPontos(1);

        itemRepository.saveAll(List.of(arma, municao, agua, comida));

        Localizacao localizacao = new Localizacao();
        localizacao.setBase("via lactea");
        localizacao.setLatitude("123lat");
        localizacao.setLongitude("123long");

        ItemDeInventario itemDeInventarioAgua = new ItemDeInventario();
        itemDeInventarioAgua.setQuantidade(12);
        itemDeInventarioAgua.setItem(agua);

        ItemDeInventario itemDeInventarioMunicao = new ItemDeInventario();
        itemDeInventarioMunicao.setQuantidade(6);
        itemDeInventarioMunicao.setItem(municao);

        ItemDeInventario itemDeInventarioArma = new ItemDeInventario();
        itemDeInventarioArma.setQuantidade(3);
        itemDeInventarioArma.setItem(arma);

        Inventario inventario = new Inventario();
//        inventario.setItensDeInventario(List.of(itemDeInventarioAgua, itemDeInventarioArma, itemDeInventarioMunicao));
        inventario.setItensDeInventario(List.of(itemDeInventarioMunicao));

        Rebelde rebelde = new Rebelde();
        rebelde.setId("luke");
        rebelde.setGenero(Genero.MASCULINO);
        rebelde.setNome("luke");
        rebelde.setIdade(33);
        rebelde.setLocalizacao(localizacao);
        rebelde.setInventario(inventario);

        rebeldeRepository.save(rebelde);

        Rebelde rebelde1 = new Rebelde();
        rebelde1.setId("john");
        rebelde1.setGenero(Genero.MASCULINO);
        rebelde1.setNome("john");

        rebeldeRepository.save(rebelde1);

        Rebelde rebelde2 = new Rebelde();
        rebelde2.setId("mike");
        rebelde2.setGenero(Genero.MASCULINO);
        rebelde2.setNome("mike");

        rebeldeRepository.save(rebelde2);

        rebelde.setId("mateo");
        rebelde.setNome("mateo");

        ItemDeInventario comidai = new ItemDeInventario();
        comidai.setItem(comida);
        comidai.setQuantidade(15);

//        rebelde.getInventario().setItensDeInventario(List.of(comidai, itemDeInventarioAgua, itemDeInventarioArma, itemDeInventarioMunicao));
        rebelde.getInventario().setItensDeInventario(List.of(comidai));

        rebeldeRepository.save(rebelde);
    }
}
