package br.com.vr.miniautorizador.controllers;

import br.com.vr.miniautorizador.dtos.request.TransacaoRequestDTO;
import br.com.vr.miniautorizador.dtos.response.TransacaoResponseDTO;
import br.com.vr.miniautorizador.services.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;


    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> realizarTransacao(@Valid @RequestBody final TransacaoRequestDTO transacaoRequestDTO) {
        final var transacaoResponseDTO = transacaoService.realizarTransacao(transacaoRequestDTO);

        return new ResponseEntity<>(transacaoResponseDTO, HttpStatus.CREATED);
    }

}
