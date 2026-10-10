package com.mariafernanda.revisa_ai.dto;

public record VeiculoRespostaDTO(
        Long id,
        String nome,
        String placa,
        String marca,
        String modelo,
        Integer ano,
        Integer quilometragemAtual
) {}