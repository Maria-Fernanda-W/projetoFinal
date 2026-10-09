package com.mariafernanda.revisa_ai.controller;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService service;

    @PostMapping
    public ResponseEntity<AgendaRespostaDTO> planejar(@Valid @RequestBody AgendaCadastroDTO dto,
                                                      Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.planejar(usuarioId, dto));
    }

    @PostMapping("/{id}/converter")
    public ResponseEntity<AgendaRespostaDTO> converter(@PathVariable Long id,
                                                       @Valid @RequestBody AgendaConversaoDTO dto,
                                                       Authentication authentication) {
        Long usuarioId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(service.converter(usuarioId, id, dto));
    }

    @GetMapping("/veiculo/{veiculoId}")
    public ResponseEntity<List<AgendaRespostaDTO>> listarPendentes(@PathVariable Long veiculoId) {
        return ResponseEntity.ok(service.listarPendentesPorVeiculo(veiculoId));
    }
}