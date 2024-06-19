package br.com.vr.miniautorizador.services;

import br.com.vr.miniautorizador.converters.CartaoConverter;
import br.com.vr.miniautorizador.dtos.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.dtos.response.SaldoResponseDTO;
import br.com.vr.miniautorizador.entities.Cartao;
import br.com.vr.miniautorizador.exceptions.CartaoJaExistenteException;
import br.com.vr.miniautorizador.exceptions.CartaoNaoEncontradoException;
import br.com.vr.miniautorizador.repositories.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    private final CartaoConverter cartaoConverter;


    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoConverter = new CartaoConverter();
    }

    public CartaoResponseDTO criar(final CartaoRequestDTO cartaoRequestDTO) {
        log.info("m=criar, cartaoRequest= {}", cartaoRequestDTO);

        verificarNumeroDuplicado(cartaoRequestDTO);

        final var cartaoEntity = cartaoConverter.toEntity(cartaoRequestDTO);
        cartaoEntity.inicializarSaldo();

        return cartaoConverter.toDTO(cartaoRepository.save(cartaoEntity));
    }

    private void verificarNumeroDuplicado(final CartaoRequestDTO cartaoRequestDTO) {
        final var numeroCartao = cartaoRequestDTO.numeroCartao();

        if (cartaoRepository.existsByNumeroCartao(numeroCartao)) {
            throw new CartaoJaExistenteException("Cartão com o número " + cartaoRequestDTO.numeroCartao() + " já existe.");
        }
    }

    public SaldoResponseDTO saldoPorNumeroCartao(final String numeroCartao) {
        log.info("m=saldoPorNumeroCartao, numeroCartao= {}", numeroCartao);

        final var saldoCartao = buscarCartaoPorNumero(numeroCartao).getSaldo();

        return new SaldoResponseDTO(saldoCartao);
    }

    private Cartao buscarCartaoPorNumero(final String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado com o número informado: " + numeroCartao));
    }

}
