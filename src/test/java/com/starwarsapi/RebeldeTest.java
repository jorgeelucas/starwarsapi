package com.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwarsapi.dto.ItemDeInventarioDTO;
import com.starwarsapi.dto.NegociacaoItemDTO;
import com.starwarsapi.dto.RebeldeNegocianteDTO;
import com.starwarsapi.enums.Genero;
import com.starwarsapi.model.Inventario;
import com.starwarsapi.model.Item;
import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.model.Localizacao;
import com.starwarsapi.model.Rebelde;
import com.starwarsapi.repository.ItemRepository;
import com.starwarsapi.repository.RebeldeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RebeldeTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RebeldeRepository rebeldeRepository;

    @MockBean
    ItemRepository itemRepository;

    Rebelde rebeldeMock1;

    Item itemAgua;
    Item itemComida;
    Item itemArma;
    Item itemMunicao;

    ItemDeInventario itemDeInventarioAgua;
    ItemDeInventario itemDeInventarioComida;
    ItemDeInventario itemDeInventarioArma;
    ItemDeInventario itemDeInventarioMunicao;

    Inventario inventarioNegociante;

    Rebelde negociante;

    @BeforeEach
    void setup() {

        itemArma = new Item();
        itemArma.setId("item-arma");
        itemArma.setNome("Arma");
        itemArma.setPontos(4);

        itemMunicao = new Item();
        itemMunicao.setId("item-municao");
        itemMunicao.setNome("Munição");
        itemMunicao.setPontos(3);

        itemAgua = new Item();
        itemAgua.setId("item-agua");
        itemAgua.setNome("Agua");
        itemAgua.setPontos(2);

        itemComida = new Item();
        itemComida.setId("item-comida");
        itemComida.setNome("Comida");
        itemComida.setPontos(1);

        itemDeInventarioArma = new ItemDeInventario();
        itemDeInventarioArma.setItem(itemArma);
        itemDeInventarioArma.setQuantidade(2);
        itemDeInventarioArma.setId("idi-arma");

        itemDeInventarioMunicao = new ItemDeInventario();
        itemDeInventarioMunicao.setItem(itemMunicao);
        itemDeInventarioMunicao.setQuantidade(3);
        itemDeInventarioMunicao.setId("idi-municao");

        itemDeInventarioAgua = new ItemDeInventario();
        itemDeInventarioAgua.setItem(itemAgua);
        itemDeInventarioAgua.setQuantidade(4);
        itemDeInventarioAgua.setId("idi-agua");

        itemDeInventarioComida = new ItemDeInventario();
        itemDeInventarioComida.setId("idi-comida");
        itemDeInventarioComida.setItem(itemComida);
        itemDeInventarioComida.setQuantidade(5);

        Inventario inventario = new Inventario();
        inventario.setId("inventarioid");
        inventario.setItensDeInventario(List.of(itemDeInventarioAgua, itemDeInventarioArma, itemDeInventarioMunicao, itemDeInventarioComida));

        inventarioNegociante = new Inventario();
        inventarioNegociante.setItensDeInventario(new ArrayList<>());

        negociante = new Rebelde();
        negociante.setInventario(inventarioNegociante);

        Localizacao starkiller= new Localizacao();
        starkiller.setLatitude("-0.3190");
        starkiller.setLongitude("-121.6336");
        starkiller.setBase("Starkiller");
        starkiller.setId("asdf");

        rebeldeMock1 = new Rebelde();
        rebeldeMock1.setId("luke");
        rebeldeMock1.setNome("luke");
        rebeldeMock1.setIdade(22);
        rebeldeMock1.setGenero(Genero.FEMININO);
        rebeldeMock1.setLocalizacao(starkiller);
        rebeldeMock1.setInventario(inventario);

        Mockito.when(rebeldeRepository.findById("luke")).thenReturn(Optional.of(rebeldeMock1));
        Mockito.when(rebeldeRepository.save(Mockito.any())).thenReturn(rebeldeMock1);

        Mockito.when(itemRepository.findById("item-agua")).thenReturn(Optional.of(itemAgua));
        Mockito.when(itemRepository.findById("item-arma")).thenReturn(Optional.of(itemArma));
        Mockito.when(itemRepository.findById("item-comida")).thenReturn(Optional.of(itemComida));
        Mockito.when(itemRepository.findById("item-municao")).thenReturn(Optional.of(itemMunicao));
    }

    @Test
    void testaAdicionarNovoRebelde() throws Exception {
        mockMvc.perform(post("/api/v1/rebeldes")
                .content(objectMapper.writeValueAsString(this.rebeldeMock1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testaAdicionarNovoRebeldeComCamposNulos() throws Exception {

        this.rebeldeMock1.setNome(null);

        mockMvc.perform(post("/api/v1/rebeldes")
                .content(objectMapper.writeValueAsString(this.rebeldeMock1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testaAdicionarItemAoRebelde() throws Exception {
        String rebeldeId = "luke";

        ItemDeInventarioDTO dto = new ItemDeInventarioDTO();
        dto.setItemId("item-agua");
        dto.setQuantidade(2);

        mockMvc.perform(post("/api/v1/rebeldes/{rebeldeid}", rebeldeId)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventario.itensDeInventario[0].quantidade").value(6));
    }

    @Test
    void testaAdicionarItemInexistenteAoRebelde() throws Exception {
        String rebeldeId = "luke";

        ItemDeInventarioDTO dto = new ItemDeInventarioDTO();
        dto.setItemId("item-inexistente");
        dto.setQuantidade(2);

        mockMvc.perform(post("/api/v1/rebeldes/" + rebeldeId)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Item item-inexistente não encontrado"));
    }

    @Test
    void testaAtualizarLocalizacaoRebelde() throws Exception {
        Localizacao localizacao = new Localizacao();
        localizacao.setBase("nova-base");
        localizacao.setLatitude("nova_latitude");
        localizacao.setLongitude("nova_logitude");

        String rebeldeid = "luke";

        mockMvc.perform(put("/api/v1/rebeldes/localizacao/{rebeldeid}", rebeldeid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localizacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localizacao.base").value("nova-base"))
                .andExpect(jsonPath("$.localizacao.latitude").value("nova_latitude"))
                .andExpect(jsonPath("$.localizacao.longitude").value("nova_logitude"));
    }

    @Test
    void testaAcusarRebeldeDeTraidor() throws Exception {
        mockMvc.perform(patch("/api/v1/rebeldes/acusar-traidor")
                .param("traidor", "luke"))
                    .andExpect(status().isNoContent());
    }

    @Test
    void testarNegociacao() throws Exception {
        // primeiro negociante
        itemDeInventarioAgua.setQuantidade(2);

        List<ItemDeInventario> listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioAgua);

        Inventario inventario1 = new Inventario();
        inventario1.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante1 = new Rebelde();
        negociante1.setId("primeiro-negociante-id");
        negociante1.setNome("primeiro-negociante-nome");
        negociante1.setInventario(inventario1);

        RebeldeNegocianteDTO rebeldeNegociante1DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante1DTO.setIdRebelde("primeiro-negociante-id");
        rebeldeNegociante1DTO.setItensDeInventario(negociante1.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("primeiro-negociante-id")).thenReturn(Optional.of(negociante1));

        // segundo negociante
        itemDeInventarioComida.setQuantidade(4);

        listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioComida);

        Inventario inventario2 = new Inventario();
        inventario2.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante2 = new Rebelde();
        negociante2.setId("segundo-negociante-id");
        negociante2.setNome("segundo-negociante-nome");
        negociante2.setInventario(inventario2);

        RebeldeNegocianteDTO rebeldeNegociante2DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante2DTO.setIdRebelde("segundo-negociante-id");
        rebeldeNegociante2DTO.setItensDeInventario(negociante2.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("segundo-negociante-id")).thenReturn(Optional.of(negociante2));

        NegociacaoItemDTO negociacaoItemDTO = new NegociacaoItemDTO();
        negociacaoItemDTO.setRebelde1(rebeldeNegociante1DTO);
        negociacaoItemDTO.setRebelde2(rebeldeNegociante2DTO);

        mockMvc.perform(post("/api/v1/rebeldes/negociar")
                .content(objectMapper.writeValueAsString(negociacaoItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testarNegociacaoComValoresIncompativeis() throws Exception {
        // primeiro negociante
        itemDeInventarioAgua.setQuantidade(3);

        List<ItemDeInventario> listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioAgua);

        Inventario inventario1 = new Inventario();
        inventario1.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante1 = new Rebelde();
        negociante1.setId("primeiro-negociante-id");
        negociante1.setNome("primeiro-negociante-nome");
        negociante1.setInventario(inventario1);

        RebeldeNegocianteDTO rebeldeNegociante1DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante1DTO.setIdRebelde("primeiro-negociante-id");
        rebeldeNegociante1DTO.setItensDeInventario(negociante1.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("primeiro-negociante-id")).thenReturn(Optional.of(negociante1));

        // segundo negociante
        itemDeInventarioComida.setQuantidade(4);

        listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioComida);

        Inventario inventario2 = new Inventario();
        inventario2.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante2 = new Rebelde();
        negociante2.setId("segundo-negociante-id");
        negociante2.setNome("segundo-negociante-nome");
        negociante2.setInventario(inventario2);

        RebeldeNegocianteDTO rebeldeNegociante2DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante2DTO.setIdRebelde("segundo-negociante-id");
        rebeldeNegociante2DTO.setItensDeInventario(negociante2.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("segundo-negociante-id")).thenReturn(Optional.of(negociante2));

        NegociacaoItemDTO negociacaoItemDTO = new NegociacaoItemDTO();
        negociacaoItemDTO.setRebelde1(rebeldeNegociante1DTO);
        negociacaoItemDTO.setRebelde2(rebeldeNegociante2DTO);

        mockMvc.perform(post("/api/v1/rebeldes/negociar")
                .content(objectMapper.writeValueAsString(negociacaoItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testarNegociacaoComTraidor() throws Exception {
        // primeiro negociante
        itemDeInventarioAgua.setQuantidade(2);

        List<ItemDeInventario> listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioAgua);

        Inventario inventario1 = new Inventario();
        inventario1.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante1 = new Rebelde();
        negociante1.setId("primeiro-negociante-id");
        negociante1.setNome("primeiro-negociante-nome");
        negociante1.setAcusacoes(4);
        negociante1.setInventario(inventario1);

        RebeldeNegocianteDTO rebeldeNegociante1DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante1DTO.setIdRebelde("primeiro-negociante-id");
        rebeldeNegociante1DTO.setItensDeInventario(negociante1.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("primeiro-negociante-id")).thenReturn(Optional.of(negociante1));

        // segundo negociante
        itemDeInventarioComida.setQuantidade(4);

        listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioComida);

        Inventario inventario2 = new Inventario();
        inventario2.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante2 = new Rebelde();
        negociante2.setId("segundo-negociante-id");
        negociante2.setNome("segundo-negociante-nome");
        negociante2.setInventario(inventario2);

        RebeldeNegocianteDTO rebeldeNegociante2DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante2DTO.setIdRebelde("segundo-negociante-id");
        rebeldeNegociante2DTO.setItensDeInventario(negociante2.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("segundo-negociante-id")).thenReturn(Optional.of(negociante2));

        NegociacaoItemDTO negociacaoItemDTO = new NegociacaoItemDTO();
        negociacaoItemDTO.setRebelde1(rebeldeNegociante1DTO);
        negociacaoItemDTO.setRebelde2(rebeldeNegociante2DTO);

        mockMvc.perform(post("/api/v1/rebeldes/negociar")
                .content(objectMapper.writeValueAsString(negociacaoItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testarNegociacaoComRebeldeSemItem() throws Exception {
        // primeiro negociante
        itemDeInventarioAgua.setQuantidade(2);

        List<ItemDeInventario> listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioAgua);

        Inventario inventario1 = new Inventario();
        inventario1.setItensDeInventario(listaDeItemDeInventario);

        Inventario inventarioSemItem = new Inventario();
        inventarioSemItem.setItensDeInventario(new ArrayList<>());

        Rebelde negociante1 = new Rebelde();
        negociante1.setId("primeiro-negociante-id");
        negociante1.setNome("primeiro-negociante-nome");
        negociante1.setAcusacoes(4);
        negociante1.setInventario(inventarioSemItem);

        RebeldeNegocianteDTO rebeldeNegociante1DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante1DTO.setIdRebelde("primeiro-negociante-id");
        rebeldeNegociante1DTO.setItensDeInventario(listaDeItemDeInventario);

        Mockito.when(rebeldeRepository.findById("primeiro-negociante-id")).thenReturn(Optional.of(negociante1));

        // segundo negociante
        itemDeInventarioComida.setQuantidade(4);

        listaDeItemDeInventario = new ArrayList<>();
        listaDeItemDeInventario.add(itemDeInventarioComida);

        Inventario inventario2 = new Inventario();
        inventario2.setItensDeInventario(listaDeItemDeInventario);

        Rebelde negociante2 = new Rebelde();
        negociante2.setId("segundo-negociante-id");
        negociante2.setNome("segundo-negociante-nome");
        negociante2.setInventario(inventario2);

        RebeldeNegocianteDTO rebeldeNegociante2DTO = new RebeldeNegocianteDTO();
        rebeldeNegociante2DTO.setIdRebelde("segundo-negociante-id");
        rebeldeNegociante2DTO.setItensDeInventario(negociante2.getInventario().getItensDeInventario());

        Mockito.when(rebeldeRepository.findById("segundo-negociante-id")).thenReturn(Optional.of(negociante2));

        NegociacaoItemDTO negociacaoItemDTO = new NegociacaoItemDTO();
        negociacaoItemDTO.setRebelde1(rebeldeNegociante1DTO);
        negociacaoItemDTO.setRebelde2(rebeldeNegociante2DTO);

        mockMvc.perform(post("/api/v1/rebeldes/negociar")
                .content(objectMapper.writeValueAsString(negociacaoItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
