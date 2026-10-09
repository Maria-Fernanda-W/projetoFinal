package com.mariafernanda.revisa_ai.exception;

public class ManutencaoNaoEncontradaException extends RuntimeException {
    public ManutencaoNaoEncontradaException(Long id) {
        super("Manutenção com id " + id + " não encontrada.");
    }
}