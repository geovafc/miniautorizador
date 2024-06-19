package br.com.vr.miniautorizador.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransacaoResponseDTO(
        String numeroCartao,
        BigDecimal valor
) {
}
