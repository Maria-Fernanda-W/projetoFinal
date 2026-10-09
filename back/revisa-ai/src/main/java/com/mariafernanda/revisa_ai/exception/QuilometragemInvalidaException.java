package com.mariafernanda.revisa_ai.exception;

public class QuilometragemInvalidaException extends RuntimeException {
    public QuilometragemInvalidaException(Integer informada, Integer atual) {
        super("A quilometragem informada (" + informada + ") é menor que a última registrada para o veículo (" + atual + ").");
    }
}