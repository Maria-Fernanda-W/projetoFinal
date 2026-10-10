package com.mariafernanda.revisa_ai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "manutencao")
@Getter
@Setter
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_manutencao_id", nullable = false)
    private TipoManutencao tipoManutencao;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_realizacao", nullable = false)
    private LocalDate dataRealizacao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column()
    private Integer quilometragem;

    @Column(name = "local_realizado")
    private String localRealizado;

    private String observacoes;
}