package com.mariafernanda.revisa_ai.exception;

public class TipoManutencaoNaoEncontradoException extends RuntimeException {
    public TipoManutencaoNaoEncontradoException(Long id) {
        super("Tipo de manutenção com id " + id + " não encontrado.");
    }
}