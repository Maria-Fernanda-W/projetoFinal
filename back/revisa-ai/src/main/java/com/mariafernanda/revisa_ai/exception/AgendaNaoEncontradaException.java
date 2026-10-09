package com.mariafernanda.revisa_ai.exception;

public class AgendaNaoEncontradaException extends RuntimeException {
    public AgendaNaoEncontradaException(Long id) {
        super("Item de agenda com id " + id + " não encontrado.");
    }
}