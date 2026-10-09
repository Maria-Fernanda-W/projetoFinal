package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TipoManutencaoDTO(
        @NotBlank String nome,
        @Positive Integer periodicidadeKm,
        @Positive Integer periodicidadeMeses
) {}