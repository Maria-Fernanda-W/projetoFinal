package com.mariafernanda.revisa_ai.exception;

public class AlvoAgendaObrigatorioException extends RuntimeException {
    public AlvoAgendaObrigatorioException() {
        super("Informe ao menos um alvo para o planejamento: data ou quilometragem.");
    }
}