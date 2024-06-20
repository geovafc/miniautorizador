package br.com.vr.miniautorizador.dtos.response;

import java.math.BigDecimal;

public record TransacaoResponseDTO(
        String numeroCartao,
        BigDecimal saldo
) {
}
