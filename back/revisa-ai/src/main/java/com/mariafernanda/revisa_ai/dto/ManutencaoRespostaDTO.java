package com.mariafernanda.revisa_ai.dto;

import java.time.LocalDate;

public record ManutencaoRespostaDTO(
        Long id,
        Long veiculoId,
        String tipoManutencaoNome,
        LocalDate dataRealizacao,
        Integer quilometragem,
        String observacoes
) {}