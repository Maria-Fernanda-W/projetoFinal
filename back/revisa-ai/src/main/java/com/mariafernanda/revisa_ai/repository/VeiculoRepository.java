package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByUsuarioId(Long usuarioId);
    Optional<Veiculo> findByPlaca(String placa);
}
