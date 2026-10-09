package com.mariafernanda.revisa_ai.exception;

public class VeiculoNaoEncontradoException extends RuntimeException {
    public VeiculoNaoEncontradoException(Long id) {
        super("Veículo com id " + id + " não encontrado.");
    }
}