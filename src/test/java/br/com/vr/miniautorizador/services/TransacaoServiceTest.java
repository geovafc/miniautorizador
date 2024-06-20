package br.com.vr.miniautorizador.services;

import br.com.vr.miniautorizador.dtos.request.TransacaoRequestDTO;
import br.com.vr.miniautorizador.entities.Cartao;
import br.com.vr.miniautorizador.exceptions.CartaoNaoEncontradoException;
import br.com.vr.miniautorizador.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exceptions.SenhaInvalidaException;
import br.com.vr.miniautorizador.repositories.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void deveriaRealizarTransacaoComSucesso() {
        final var  numeroCartaoSalvo = "1234567890123456";
        final var senhaCartaoSalvo = "1234";
        final var saldoCartaoSalvo = new BigDecimal("200.00");
        final var saldoCartaoEsperado = new BigDecimal("100.00");

        final var cartaoFake = builderCartaoFake(numeroCartaoSalvo, senhaCartaoSalvo, saldoCartaoSalvo);
        final var cartaoFakeSaldoAtualizado = builderCartaoFake(numeroCartaoSalvo, senhaCartaoSalvo, saldoCartaoEsperado).get();

        when(cartaoRepository.findByNumeroCartao(numeroCartaoSalvo)).thenReturn(cartaoFake);

        when(cartaoRepository.save(cartaoFakeSaldoAtualizado)).thenReturn(cartaoFakeSaldoAtualizado);

        final var valorTransacaoEnviada = new BigDecimal("100.00");
        final var transacaoEnviada = new TransacaoRequestDTO(numeroCartaoSalvo, senhaCartaoSalvo, valorTransacaoEnviada);

        final var transacaoRetornada = transacaoService.realizarTransacao(transacaoEnviada);


        assertEquals(saldoCartaoEsperado, transacaoRetornada.saldo());
    }

    @Test
    void deveriaRealizarTransacaoComErroCartaoNaoEncontrado() {
        final var numeroCartaoEnviado = "9876543210987654";
        final var senhaEnviada = "1234";
        final var valorTransacaoEnviada = new BigDecimal("100.00");

        when(cartaoRepository.findByNumeroCartao(numeroCartaoEnviado)).thenThrow(new CartaoNaoEncontradoException("Cartão não encontrado com o número informado: " + numeroCartaoEnviado));

        final var transacaoEnviada = new TransacaoRequestDTO(numeroCartaoEnviado, senhaEnviada, valorTransacaoEnviada);

        assertThatExceptionOfType(CartaoNaoEncontradoException.class)
                .isThrownBy(() -> transacaoService.realizarTransacao(transacaoEnviada));
    }

    @Test
    void deveriaRealizarTransacaoComErroSenhaInvalida() {
        final var  numeroCartaoSalvo = "1234567890123456";
        final var senhaCartaoSalvo = "1234";
        final var saldoCartaoSalvo = new BigDecimal("200.00");

        final var cartaoFake = builderCartaoFake(numeroCartaoSalvo, senhaCartaoSalvo, saldoCartaoSalvo);

        when(cartaoRepository.findByNumeroCartao(numeroCartaoSalvo)).thenReturn(cartaoFake);

        final var valorTransacaoEnviada = new BigDecimal("100.00");
        final var senhaCartaoEnviada = "3244";

        final var transacaoEnviada = new TransacaoRequestDTO(numeroCartaoSalvo, senhaCartaoEnviada, valorTransacaoEnviada);

        assertThatExceptionOfType(SenhaInvalidaException.class)
                .isThrownBy(() -> transacaoService.realizarTransacao(transacaoEnviada));
    }

    @Test
    void deveriaRealizarTransacaoComErroSaldoInsuficiente() {
        final var  numeroCartaoSalvo = "1234567890123456";
        final var senhaCartaoSalvo = "1234";
        final var saldoCartaoSalvo = new BigDecimal("200.00");

        final var cartaoFake = builderCartaoFake(numeroCartaoSalvo, senhaCartaoSalvo, saldoCartaoSalvo);

        when(cartaoRepository.findByNumeroCartao(numeroCartaoSalvo)).thenReturn(cartaoFake);

        final var valorTransacaoEnviada = new BigDecimal("550.00");

        final var transacaoEnviada = new TransacaoRequestDTO(numeroCartaoSalvo, senhaCartaoSalvo, valorTransacaoEnviada);

        assertThatExceptionOfType(SaldoInsuficienteException.class)
                .isThrownBy(() -> transacaoService.realizarTransacao(transacaoEnviada));
    }

    private static Optional<Cartao> builderCartaoFake(final String numeroCartaoSalvo, final String senhaCartaoSalvo, final BigDecimal saldoCartaoSalvo) {
        return Optional.ofNullable(Cartao.builder()
                .numeroCartao(numeroCartaoSalvo)
                .senha(senhaCartaoSalvo)
                .saldo(saldoCartaoSalvo)
                .build());
    }


}
