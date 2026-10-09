package com.mariafernanda.revisa_ai.dto;

public record TipoManutencaoRespostaDTO(
        Long id,
        String nome,
        Integer periodicidadeKm,
        Integer periodicidadeMeses,
        boolean padrao
) {}