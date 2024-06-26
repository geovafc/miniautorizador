package br.com.vr.miniautorizador.repositories;

import br.com.vr.miniautorizador.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsByNumeroCartao(String numeroCartao);

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
