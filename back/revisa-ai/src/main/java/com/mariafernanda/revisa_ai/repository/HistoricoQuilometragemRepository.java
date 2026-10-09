package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.HistoricoQuilometragem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoricoQuilometragemRepository extends JpaRepository<HistoricoQuilometragem, Long> {
    List<HistoricoQuilometragem> findByVeiculoIdOrderByDataRegistroDesc(Long veiculoId);
    Optional<HistoricoQuilometragem> findTopByVeiculoIdOrderByDataRegistroDesc(Long veiculoId);
}
