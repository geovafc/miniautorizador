package br.com.vr.miniautorizador.exceptions;

public class CartaoJaExistenteException extends RuntimeException {

    public CartaoJaExistenteException(String message) {
        super(message);
    }
}
