package br.com.vr.miniautorizador.controller;

import br.com.vr.miniautorizador.dto.request.TransacaoRequestDTO;
import br.com.vr.miniautorizador.dto.response.TransacaoResponseDTO;
import br.com.vr.miniautorizador.service.TransacaoService;
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
