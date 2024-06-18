package br.com.vr.miniautorizador.repositories;

import br.com.vr.miniautorizador.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsByNumeroCartao(String numeroCartao);

}
