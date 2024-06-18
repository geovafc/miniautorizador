package br.com.vr.miniautorizador.entities;


import br.com.vr.miniautorizador.dto.response.CartaoResponseDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Builder
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCartao;

    private String senha;

    private BigDecimal saldo;

    public void inicializarSaldo(){
        this.saldo = new BigDecimal("500.00");
    }

    public CartaoResponseDTO toDTO(){
        return new CartaoResponseDTO(this.id, this.numeroCartao, this.senha, this.saldo);
    }

}