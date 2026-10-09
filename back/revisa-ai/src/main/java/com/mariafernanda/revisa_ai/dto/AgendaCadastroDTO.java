package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AgendaCadastroDTO(
        @NotNull Long veiculoId,
        @NotNull Long tipoManutencaoId,
        LocalDate dataAlvo,
        Integer quilometragemAlvo
) {}