package com.mariafernanda.revisa_ai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_quilometragem")
@Getter
@Setter
public class HistoricoQuilometragem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(nullable = false)
    private Integer quilometragem;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;
}