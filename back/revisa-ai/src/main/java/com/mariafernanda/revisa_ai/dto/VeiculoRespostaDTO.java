package com.mariafernanda.revisa_ai.dto;

public record VeiculoRespostaDTO(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer ano,
        Integer quilometragemAtual
) {}