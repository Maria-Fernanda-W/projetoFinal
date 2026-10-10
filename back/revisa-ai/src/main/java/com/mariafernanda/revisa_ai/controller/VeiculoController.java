package com.mariafernanda.revisa_ai.controller;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoRespostaDTO> cadastrar(@Valid @RequestBody VeiculoCadastroDTO dto,
                                                        Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.cadastrar(usuarioId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoRespostaDTO> alterar(@PathVariable Long id,
                                                      @Valid @RequestBody VeiculoAlteracaoDTO dto,
                                                      Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(veiculoService.alterar(usuarioId, id, dto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VeiculoRespostaDTO>> listarPorUsuario(@PathVariable Long usuarioId,
                                                                     Authentication authentication) {
        Long usuarioIdAutenticado = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(veiculoService.listarPorUsuario(usuarioIdAutenticado, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        veiculoService.deletar(usuarioId, id);
        return ResponseEntity.noContent().build();
    }
}