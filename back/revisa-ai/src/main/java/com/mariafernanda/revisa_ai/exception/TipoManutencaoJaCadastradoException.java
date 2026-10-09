package com.mariafernanda.revisa_ai.exception;

public class TipoManutencaoJaCadastradoException extends RuntimeException {
    public TipoManutencaoJaCadastradoException(String nome) {
        super("O tipo de manutenção '" + nome + "' já está cadastrado.");
    }
}
