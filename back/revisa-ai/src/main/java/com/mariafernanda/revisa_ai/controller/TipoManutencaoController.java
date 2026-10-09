package com.mariafernanda.revisa_ai.controller;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.service.TipoManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-manutencao")
@RequiredArgsConstructor
public class TipoManutencaoController {

    private final TipoManutencaoService service;

    @PostMapping
    public ResponseEntity<TipoManutencaoRespostaDTO> cadastrar(@Valid @RequestBody TipoManutencaoDTO dto,
                                                               Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuarioId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoManutencaoRespostaDTO> alterar(@PathVariable Long id,
                                                             @Valid @RequestBody TipoManutencaoDTO dto,
                                                             Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.alterar(usuarioId, id, dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoManutencaoRespostaDTO>> listarDisponiveis(Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.listarDisponiveisParaUsuario(usuarioId));
    }
}