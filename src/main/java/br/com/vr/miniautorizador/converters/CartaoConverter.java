package br.com.vr.miniautorizador.converters;

import br.com.vr.miniautorizador.dtos.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.entities.Cartao;

public class CartaoConverter {

    public CartaoResponseDTO toDTO(final Cartao cartao){
        return new CartaoResponseDTO(cartao.getId(), cartao.getNumeroCartao(), cartao.getSenha(), cartao.getSaldo());
    }

    public Cartao toEntity(final CartaoRequestDTO cartaoRequestDTO){
        return Cartao.builder()
                .numeroCartao(cartaoRequestDTO.numeroCartao())
                .senha(cartaoRequestDTO.senha())
                .build();
    }


}
