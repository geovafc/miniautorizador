package br.com.vr.miniautorizador.services;

import br.com.vr.miniautorizador.dtos.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.dtos.response.SaldoResponseDTO;
import br.com.vr.miniautorizador.entities.Cartao;
import br.com.vr.miniautorizador.exceptions.CartaoJaExistenteException;
import br.com.vr.miniautorizador.exceptions.CartaoNaoEncontradoException;
import br.com.vr.miniautorizador.mappers.CartaoMapper;
import br.com.vr.miniautorizador.repositories.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    private final CartaoMapper cartaoMapper;

    public CartaoService(CartaoRepository cartaoRepository, CartaoMapper cartaoMapper) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoMapper = cartaoMapper;
    }

    public CartaoResponseDTO criar(final CartaoRequestDTO cartaoRequestDTO) {
        log.info("m=criar, cartaoRequest= {}", cartaoRequestDTO);

        verificarNumeroDuplicado(cartaoRequestDTO);

//        final var cartaoEntity = cartaoRequestDTO.toEntity();
        final var cartaoEntity = cartaoMapper.toEntity(cartaoRequestDTO);
        cartaoEntity.inicializarSaldo();

        final var cartaoResponseDTO = cartaoMapper.toDTO(cartaoRepository.save(cartaoEntity));

//        return cartaoRepository.save(cartaoEntity).toDTO();
        return cartaoResponseDTO;
    }

    private void verificarNumeroDuplicado(CartaoRequestDTO cartaoRequestDTO) {
        final var numeroCartao = cartaoRequestDTO.numeroCartao();

        if (cartaoRepository.existsByNumeroCartao(numeroCartao)) {
            throw new CartaoJaExistenteException("Cartão com o número " + cartaoRequestDTO.numeroCartao() + " já existe.");
        }
    }

    public SaldoResponseDTO saldoPorNumeroCartao(String numeroCartao) {
        log.info("m=saldoPorNumeroCartao, numeroCartao= {}", numeroCartao);

        final var saldoResponseDTO = buscarCartaoPorNumero(numeroCartao).toSaldoResponseDTO();

        return saldoResponseDTO;
    }

    private Cartao buscarCartaoPorNumero(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado com o número informado: " + numeroCartao));
    }

}
