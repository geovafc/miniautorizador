package br.com.vr.miniautorizador.mappers;

import br.com.vr.miniautorizador.dtos.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.entities.Cartao;
import org.mapstruct.Mapper;

@Mapper
public interface CartaoMapper {

    CartaoResponseDTO toDTO(Cartao cartao);

    Cartao toEntity(CartaoRequestDTO cartaoRequestDTO);
}