package br.com.vr.miniautorizador.controller;

import br.com.vr.miniautorizador.dto.request.CartaoRequestDTO;
import br.com.vr.miniautorizador.dto.response.CartaoResponseDTO;
import br.com.vr.miniautorizador.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> criar(@Valid @RequestBody final CartaoRequestDTO cartaoRequestDTO) {
        final var cartaoResponseDTO = cartaoService.criar(cartaoRequestDTO);

        return new ResponseEntity<>(cartaoResponseDTO, HttpStatus.CREATED);
    }
}
