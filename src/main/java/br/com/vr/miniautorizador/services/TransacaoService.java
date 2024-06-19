package br.com.vr.miniautorizador.services;

import br.com.vr.miniautorizador.dtos.request.TransacaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.TransacaoResponseDTO;
import br.com.vr.miniautorizador.entities.Cartao;
import br.com.vr.miniautorizador.exceptions.CartaoNaoEncontradoException;
import br.com.vr.miniautorizador.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exceptions.SenhaInvalidaException;
import br.com.vr.miniautorizador.repositories.CartaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class TransacaoService {

    private final CartaoRepository cartaoRepository;

    public TransacaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public TransacaoResponseDTO realizarTransacao(final TransacaoRequestDTO transacaoRequestDTO) {
//        todo: add logs
        final var cartao = buscarCartaoPorNumero(transacaoRequestDTO.numeroCartao());

        validarSenha(cartao, transacaoRequestDTO.senha());
        verificarSaldoSuficiente(cartao, transacaoRequestDTO.valor());

        cartao.diminuirSaldo(transacaoRequestDTO.valor());

        final var cartaoComSaldoAtualizado = cartaoRepository.save(cartao);

//        Todo
//        Fazer uso do mapper
        return new TransacaoResponseDTO(cartaoComSaldoAtualizado.getNumeroCartao(), cartaoComSaldoAtualizado.getSaldo());
    }

    private Cartao buscarCartaoPorNumero(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado com o número informado: " + numeroCartao));
    }

    private void validarSenha(Cartao cartao, String senhaInformada) {
        if (!cartao.getSenha().equals(senhaInformada)) {
            throw new SenhaInvalidaException("Senha incorreta para o cartão: " + cartao.getNumeroCartao());
        }
    }

    private void verificarSaldoSuficiente(Cartao cartao, BigDecimal valorTransacao) {
        if (cartao.getSaldo().compareTo(valorTransacao) < 0) {
//            todo enviar como parâmetro somente os valores e deixar que a classe de exceção tenha a mensagem com o erro
            final var mensagemErro = String.format("Saldo insuficiente para realizar a transação. Saldo atual: R$ %.2f. valor da transação: R$ %.2f.", cartao.getSaldo(), valorTransacao);
            throw new SaldoInsuficienteException(mensagemErro);
        }
    }
}
