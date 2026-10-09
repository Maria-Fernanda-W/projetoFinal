package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ManutencaoCadastroDTO(
        @NotNull Long veiculoId,
        @NotNull Long tipoManutencaoId,
        @NotNull LocalDate dataRealizacao,
        @NotNull @PositiveOrZero Integer quilometragem,
        String observacoes
) {}