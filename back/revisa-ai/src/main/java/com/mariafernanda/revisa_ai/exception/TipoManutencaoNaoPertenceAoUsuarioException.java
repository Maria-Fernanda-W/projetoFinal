package com.mariafernanda.revisa_ai.exception;

public class TipoManutencaoNaoPertenceAoUsuarioException extends RuntimeException {
    public TipoManutencaoNaoPertenceAoUsuarioException(Long id) {
        super("O tipo de manutenção com id " + id + " não pertence a este usuário.");
    }
}