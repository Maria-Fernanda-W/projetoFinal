package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record AgendaConversaoDTO(
        @NotNull LocalDate dataRealizacao,
        @NotNull @PositiveOrZero Integer quilometragem,
        String observacoes
) {}