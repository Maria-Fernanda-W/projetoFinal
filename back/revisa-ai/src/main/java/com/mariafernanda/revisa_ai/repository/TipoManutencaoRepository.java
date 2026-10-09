package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.TipoManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TipoManutencaoRepository extends JpaRepository<TipoManutencao, Long> {
    Optional<TipoManutencao> findByNomeAndUsuarioIsNull(String nome);
    Optional<TipoManutencao> findByNomeAndUsuarioId(String nome, Long usuarioId);

    @Query("SELECT t FROM TipoManutencao t WHERE t.usuario.id = :usuarioId OR " +
            "(t.usuario IS NULL AND t.id NOT IN " +
            "(SELECT p.origemPadraoId FROM TipoManutencao p WHERE p.usuario.id = :usuarioId AND p.origemPadraoId IS NOT NULL))")
    List<TipoManutencao> findDisponiveisParaUsuario(@Param("usuarioId") Long usuarioId);

    Optional<TipoManutencao> findByOrigemPadraoIdAndUsuarioId(Long origemPadraoId, Long usuarioId);
}