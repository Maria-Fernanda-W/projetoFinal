package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByVeiculoIdOrderByDataRealizacaoDesc(Long veiculoId);
    Optional<Manutencao> findTopByVeiculoIdAndTipoManutencaoIdOrderByDataRealizacaoDesc(Long veiculoId, Long tipoManutencaoId);
}