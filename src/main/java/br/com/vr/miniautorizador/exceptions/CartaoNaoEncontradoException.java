package br.com.vr.miniautorizador.exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {

    public CartaoNaoEncontradoException(String message) {
        super(message);
    }
}
