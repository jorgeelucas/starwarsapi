package com.starwarsapi.service;

import com.starwarsapi.dto.ItemDeInventarioDTO;
import com.starwarsapi.dto.LocalizacaoDTO;
import com.starwarsapi.dto.NegociacaoItemDTO;
import com.starwarsapi.dto.PaganteRecebedorDTO;
import com.starwarsapi.dto.RebeldeDTO;
import com.starwarsapi.dto.RebeldeNegocianteDTO;
import com.starwarsapi.model.Inventario;
import com.starwarsapi.model.Item;
import com.starwarsapi.model.ItemDeInventario;
import com.starwarsapi.model.Localizacao;
import com.starwarsapi.model.Rebelde;
import com.starwarsapi.repository.RebeldeRepository;
import com.starwarsapi.service.exception.NaoPossuiItemException;
import com.starwarsapi.service.exception.ObjetoNaoEncontradoException;
import com.starwarsapi.service.exception.QuantidadeInsuficienteException;
import com.starwarsapi.service.exception.TraidorException;
import com.starwarsapi.service.exception.ValoresIncompativeisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.starwarsapi.converter.Converter.convert;

@Service
public class RebeldeService {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private InventarioService inventarioService;

    public Rebelde adicionarRebelde(final RebeldeDTO rebeldeDto) {
        Rebelde rebelde = convert(rebeldeDto, Rebelde.class);

        validarInventario(rebelde.getInventario());

        rebelde.setId(UUID.randomUUID().toString());
        return salvarOuAtualizarRebelde(rebelde);
    }

    public Rebelde atualizarLocalizacaoRebelde(final String idRebelde, final LocalizacaoDTO localizacaoDto) {
        Rebelde rebelde = buscarPorId(idRebelde);
        rebelde.setLocalizacao(convert(localizacaoDto, Localizacao.class));
        return salvarOuAtualizarRebelde(rebelde);
    }

    public void acusarTraidor(final String traidor) {
        Rebelde rebelde = buscarPorId(traidor);
        rebelde.setAcusacoes(rebelde.getAcusacoes()+1);
        salvarOuAtualizarRebelde(rebelde);
    }

    public void negociacao(final NegociacaoItemDTO negociacaoItemDTO) {
        RebeldeNegocianteDTO primeiroRebeldeNegocianteDTO = negociacaoItemDTO.getRebelde1();
        RebeldeNegocianteDTO segundoRebeldeNEgocianteDTO = negociacaoItemDTO.getRebelde2();

        Rebelde primeiroRebelde =  validarRebelde(primeiroRebeldeNegocianteDTO.getIdRebelde());
        Rebelde segundoRebelde =  validarRebelde(segundoRebeldeNEgocianteDTO.getIdRebelde());

        PaganteRecebedorDTO transacaoIda = PaganteRecebedorDTO.builder()
                .pagante(primeiroRebelde)
                .recebedor(segundoRebelde)
                .itensDeInventario(negociacaoItemDTO.getRebelde1().getItensDeInventario())
                .build();

        PaganteRecebedorDTO transacaoVolta = PaganteRecebedorDTO.builder()
                .pagante(segundoRebelde)
                .recebedor(primeiroRebelde)
                .itensDeInventario(negociacaoItemDTO.getRebelde2().getItensDeInventario())
                .build();

        validarItensDoRebelde(transacaoIda.getPagante(), transacaoIda.getItensDeInventario());
        validarItensDoRebelde(transacaoVolta.getPagante(), transacaoVolta.getItensDeInventario());

        validarValoresDaNegociacao(primeiroRebeldeNegocianteDTO, segundoRebeldeNEgocianteDTO);

        efetuarTransacao(transacaoIda, transacaoVolta);
    }

    public Rebelde addItem(final String idRebelde, final ItemDeInventarioDTO itemDeInventarioDTO) {
        Rebelde rebelde = buscarPorId(idRebelde);
        Item novoItem = itemService.obterPorId(itemDeInventarioDTO.getItem().getId());

        ItemDeInventario itemDeInventario = new ItemDeInventario();
        itemDeInventario.setItem(novoItem);
        itemDeInventario.setQuantidade(itemDeInventarioDTO.getQuantidade());

        inventarioService.addItem(rebelde.getInventario(), List.of(itemDeInventario));
        return salvarOuAtualizarRebelde(rebelde);
    }

    public List<Rebelde> buscarTodos() {
        return rebeldeRepository.findAll();
    }

    public Rebelde buscarPorId(final String id) {
        return rebeldeRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("rebelde de id " + id + " nao encontrado"));
    }

    public List<Rebelde> buscarPorQuantidadeDeAcusacoesIgualOuMaiorQue(Integer qtdAcusacoes) {
        return rebeldeRepository.findAllByAcusacoesGreaterThanEqual(qtdAcusacoes);
    }

    private void efetuarTransacao(final PaganteRecebedorDTO transacaoIda, final PaganteRecebedorDTO transacaoVolta) {
        adicionarERemoverItensNegociados(transacaoIda);
        adicionarERemoverItensNegociados(transacaoVolta);

        salvarOuAtualizarRebelde(List.of(transacaoIda.getPagante(), transacaoIda.getRecebedor()));
    }

    private void adicionarERemoverItensNegociados(final PaganteRecebedorDTO transacao) {
        transacao.getItensDeInventario().stream()
                .forEach(itemDeInventario -> {
                    inventarioService.addItem(transacao.getRecebedor().getInventario(), transacao.getItensDeInventario());
                    inventarioService.removerItem(transacao.getPagante().getInventario(), transacao.getItensDeInventario());
                });
    }

    private void validarItensDoRebelde(final Rebelde rebelde, final List<ItemDeInventario> itens) {
        itens.stream().forEach(itemDeInventario -> {
            boolean isValidItem = rebelde.getInventario().getItensDeInventario()
                    .stream().anyMatch(itemDeInventarioDoRebelde -> {
                        if (itemDeInventario.getItem().getId().equals(itemDeInventarioDoRebelde.getItem().getId())) {
                            if (itemDeInventario.getQuantidade() > itemDeInventarioDoRebelde.getQuantidade()) {
                                throw new QuantidadeInsuficienteException("rebelde " + rebelde.getNome() + " não possui quantidade suficiente de " + itemDeInventario.getItem().getNome());
                            }
                            return true;
                        } else {
                            return false;
                        }
                    });
            if (!isValidItem) {
                throw new NaoPossuiItemException("rebelde " + rebelde.getNome() + " não possui o item " + itemDeInventario.getItem().getNome());
            }
        });
    }

    private Rebelde validarRebelde(final String idRebelde) {
        Rebelde rebelde = buscarPorId(idRebelde);
        if (rebelde.getAcusacoes() > 3) {
            throw new TraidorException("O rebelde " + rebelde.getNome() + " tem 3 ou mais acusações de traição!!!");
        }
        return rebelde;
    }

    private void validarValoresDaNegociacao(final RebeldeNegocianteDTO rebelde1, final RebeldeNegocianteDTO rebelde2) {
        Integer totalValorItensRebelde1 = rebelde1.getItensDeInventario().stream()
                .map(itemDeInventario -> itemDeInventario.getItem().getPontos() * itemDeInventario.getQuantidade())
                .reduce(0, (subtotal, element) -> subtotal + element);

        Integer totalValorItensRebelde2 = rebelde2.getItensDeInventario().stream()
                .map(itemDeInventario -> itemDeInventario.getItem().getPontos() * itemDeInventario.getQuantidade())
                .reduce(0, (subtotal, element) -> subtotal + element);


        if (totalValorItensRebelde1 != totalValorItensRebelde2) {
            throw new ValoresIncompativeisException("valores incompativeis " + totalValorItensRebelde1 + " e " + totalValorItensRebelde2);
        }
    }

    private void validarInventario(final Inventario inventario) {
        inventario.getItensDeInventario()
                .stream().forEach(itemDeInventario -> {
                    if (itemDeInventario.getQuantidade() < 1) {
                        throw new QuantidadeInsuficienteException("Quantidade de itens invalida: " + itemDeInventario.getQuantidade());
                    }
                    itemService.obterPorId(itemDeInventario.getItem().getId());
        });
    }

    private Rebelde salvarOuAtualizarRebelde(final Rebelde rebelde) {
        return rebeldeRepository.save(rebelde);
    }

    private List<Rebelde> salvarOuAtualizarRebelde(final List<Rebelde> rebeldes) {
        return rebeldeRepository.saveAll(rebeldes);
    }
}
