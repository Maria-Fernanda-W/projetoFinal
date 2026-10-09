package com.mariafernanda.revisa_ai.exception;

public class AgendaJaConcluidaException extends RuntimeException {
    public AgendaJaConcluidaException(Long id) {
        super("O item de agenda " + id + " já foi convertido em manutenção.");
    }
}