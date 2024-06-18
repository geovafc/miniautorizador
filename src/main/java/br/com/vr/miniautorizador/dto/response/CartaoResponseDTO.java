package br.com.vr.miniautorizador.dto.response;

import java.math.BigDecimal;

public record CartaoResponseDTO(Long id, String numeroCartao, String senha, BigDecimal saldo) {


}
