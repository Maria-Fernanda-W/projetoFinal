package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AgendaConversaoDTO(
        @NotBlank String nome,
        @NotNull LocalDate dataRealizacao,
        @NotNull @PositiveOrZero BigDecimal valor,
        @PositiveOrZero Integer quilometragem,
        String localRealizado,
        String observacoes
) {}