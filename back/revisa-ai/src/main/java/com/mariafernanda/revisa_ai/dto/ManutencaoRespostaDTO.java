package com.mariafernanda.revisa_ai.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ManutencaoRespostaDTO(
        Long id,
        Long veiculoId,
        String veiculoNome,
        String tipoManutencaoNome,
        String nome,
        LocalDate dataRealizacao,
        BigDecimal valor,
        Integer quilometragem,
        String localRealizado,
        String observacoes
) {}