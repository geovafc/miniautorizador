package br.com.vr.miniautorizador.dtos.request;

import br.com.vr.miniautorizador.entities.Cartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CartaoRequestDTO(
        @NotBlank
        @Pattern(regexp = "[0-9]{16}")
        String numeroCartao,
        @NotBlank
        String senha) {
}
