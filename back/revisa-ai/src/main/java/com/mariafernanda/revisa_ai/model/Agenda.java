package com.mariafernanda.revisa_ai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "agenda")
@Getter
@Setter
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_manutencao_id", nullable = false)
    private TipoManutencao tipoManutencao;

    @Column(name = "data_alvo")
    private LocalDate dataAlvo;

    @Column(name = "quilometragem_alvo")
    private Integer quilometragemAlvo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgenda status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manutencao_id")
    private Manutencao manutencao; // preenchido só quando convertida (RF11)
}