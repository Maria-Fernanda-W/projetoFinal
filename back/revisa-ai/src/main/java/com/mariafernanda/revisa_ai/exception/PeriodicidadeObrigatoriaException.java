package com.mariafernanda.revisa_ai.exception;

public class PeriodicidadeObrigatoriaException extends RuntimeException {
    public PeriodicidadeObrigatoriaException() {
        super("Informe ao menos uma periodicidade: por quilometragem ou por tempo (meses).");
    }
}