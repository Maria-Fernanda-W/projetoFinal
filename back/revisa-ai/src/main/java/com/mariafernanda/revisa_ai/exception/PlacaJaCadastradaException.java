package com.mariafernanda.revisa_ai.exception;

public class PlacaJaCadastradaException extends RuntimeException {
    public PlacaJaCadastradaException(String placa) {
        super("A placa " + placa + " já está cadastrada.");
    }
}
