package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ManutencaoAlteracaoDTO(
        @NotNull Long tipoManutencaoId,
        @NotBlank String nome,
        @NotNull LocalDate dataRealizacao,
        @NotNull @PositiveOrZero BigDecimal valor,
        String localRealizado,
        String observacoes
) {}