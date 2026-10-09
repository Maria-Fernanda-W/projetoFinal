package com.mariafernanda.revisa_ai.dto;

import java.time.LocalDate;

public record AgendaRespostaDTO(
        Long id,
        Long veiculoId,
        String tipoManutencaoNome,
        LocalDate dataAlvo,
        Integer quilometragemAlvo,
        String status,
        Long manutencaoId
) {}