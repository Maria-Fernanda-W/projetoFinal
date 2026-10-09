package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.Agenda;
import com.mariafernanda.revisa_ai.model.StatusAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByVeiculoIdAndStatus(Long veiculoId, StatusAgenda status);
}