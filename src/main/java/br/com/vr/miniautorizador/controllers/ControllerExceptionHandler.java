package br.com.vr.miniautorizador.controllers;

import br.com.vr.miniautorizador.dtos.response.ErrorResponseDTO;
import br.com.vr.miniautorizador.exceptions.CartaoJaExistenteException;
import br.com.vr.miniautorizador.exceptions.CartaoNaoEncontradoException;
import br.com.vr.miniautorizador.exceptions.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exceptions.SenhaInvalidaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class, SaldoInsuficienteException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponseDTO badRequest(final Throwable exception) {
        final var exceptionMessage = exception.getMessage();

        log.error("m=badRequest, ex= {}", exceptionMessage);

        return new ErrorResponseDTO(exceptionMessage, System.currentTimeMillis());
    }

    @ResponseBody
    @ExceptionHandler({CartaoJaExistenteException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO conflict(final Throwable exception) {
        final var exceptionMessage = exception.getMessage();

        log.error("m=conflict, ex= {}", exceptionMessage);

        return new ErrorResponseDTO(exceptionMessage, System.currentTimeMillis());
    }

    @ResponseBody
    @ExceptionHandler({CartaoNaoEncontradoException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO notFound(final Throwable exception) {
        final var exceptionMessage = exception.getMessage();

        log.error("m=notFound, ex= {}", exceptionMessage);

        return new ErrorResponseDTO(exceptionMessage, System.currentTimeMillis());
    }

    @ResponseBody
    @ExceptionHandler({SenhaInvalidaException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO unauthorized(final Throwable exception) {
        final var exceptionMessage = exception.getMessage();

        log.error("m=unauthorized, ex= {}", exceptionMessage);

        return new ErrorResponseDTO(exceptionMessage, System.currentTimeMillis());
    }
}