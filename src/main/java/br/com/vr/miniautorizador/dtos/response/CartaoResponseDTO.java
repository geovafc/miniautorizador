package br.com.vr.miniautorizador.dtos.response;

import java.math.BigDecimal;

public record CartaoResponseDTO(Long id, String numeroCartao, String senha, BigDecimal saldo) {


}
