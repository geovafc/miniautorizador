package br.com.vr.miniautorizador.exceptions;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String message) {
        super(message);
    }
}