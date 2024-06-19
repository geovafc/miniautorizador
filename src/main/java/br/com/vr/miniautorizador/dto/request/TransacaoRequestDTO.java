package br.com.vr.miniautorizador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransacaoRequestDTO(
        @NotBlank
        @Pattern(regexp = "[0-9]{16}")
        String numeroCartao,
        @NotBlank
        String senha,
        @NotNull
        BigDecimal valor
) {
}
