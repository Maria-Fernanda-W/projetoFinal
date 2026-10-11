package com.mariafernanda.revisa_ai.controller;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {

    private final ManutencaoService service;

    @PostMapping
    public ResponseEntity<ManutencaoRespostaDTO> cadastrar(@Valid @RequestBody ManutencaoCadastroDTO dto,
                                                           Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuarioId, dto));
    }

    @GetMapping("/veiculo/{veiculoId}")
    public ResponseEntity<List<ManutencaoRespostaDTO>> listarPorVeiculo(@PathVariable Long veiculoId,
                                                                        Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.listarPorVeiculo(usuarioId, veiculoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoRespostaDTO> alterar(@PathVariable Long id,
                                                         @Valid @RequestBody ManutencaoAlteracaoDTO dto,
                                                         Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.alterar(usuarioId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        service.deletar(usuarioId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ManutencaoRespostaDTO>> listarPorUsuario(@PathVariable Long usuarioId,
                                                                        Authentication authentication) {
        Long usuarioIdAutenticado = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.listarPorUsuario(usuarioIdAutenticado, usuarioId));
    }
}