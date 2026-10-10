package com.mariafernanda.revisa_ai.dto;

import jakarta.validation.constraints.*;

public record VeiculoAlteracaoDTO(
        @NotBlank String nome,
        @NotBlank String placa,
        @NotBlank String marca,
        @NotBlank String modelo,
        @NotNull @Min(1950) Integer ano
) {}
