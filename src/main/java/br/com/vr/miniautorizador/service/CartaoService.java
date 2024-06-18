package br.com.vr.miniautorizador.service;

import br.com.vr.miniautorizador.dto.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dto.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.exceptions.CartaoJaExistenteException;
import br.com.vr.miniautorizador.repositories.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public CartaoResponseDTO criar(final CartaoRequestDTO cartaoRequestDTO) {
        log.info("m=criar, cartaoRequest= {}", cartaoRequestDTO);

        verificarNumeroDuplicado(cartaoRequestDTO);

        final var cartaoEntity = cartaoRequestDTO.toEntity();
        cartaoEntity.inicializarSaldo();

        return cartaoRepository.save(cartaoEntity).toDTO();
    }

    private void verificarNumeroDuplicado(CartaoRequestDTO cartaoRequestDTO) {
        final var numeroCartao = cartaoRequestDTO.numeroCartao();

        if (cartaoRepository.existsByNumeroCartao(numeroCartao)) {
            throw new CartaoJaExistenteException("Cartão com o número " + cartaoRequestDTO.numeroCartao() + " já existe.");
        }
    }

}
