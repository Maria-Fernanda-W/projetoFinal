package com.mariafernanda.revisa_ai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_manutencao")
@Getter
@Setter
public class TipoManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // null = tipo padrão (global); preenchido = tipo personalizado do usuário

    @Column(name = "origem_padrao_id")
    private Long origemPadraoId; // preenchido só quando este registro é uma cópia personalizada de um tipo padrão

    @Column(nullable = false)
    private String nome;

    @Column(name = "periodicidade_km")
    private Integer periodicidadeKm;

    @Column(name = "periodicidade_meses")
    private Integer periodicidadeMeses;
}