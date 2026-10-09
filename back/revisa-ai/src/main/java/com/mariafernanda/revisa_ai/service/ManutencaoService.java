package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.*;
import com.mariafernanda.revisa_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final TipoManutencaoRepository tipoManutencaoRepository;
    private final HistoricoQuilometragemRepository historicoRepository;
    private final VeiculoService veiculoService;

    public ManutencaoRespostaDTO cadastrar(Long usuarioId, ManutencaoCadastroDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException(dto.veiculoId()));

        veiculoService.validarPropriedade(veiculo, usuarioId);

        TipoManutencao tipo = tipoManutencaoRepository.findById(dto.tipoManutencaoId())
                .orElseThrow(() -> new TipoManutencaoNaoEncontradoException(dto.tipoManutencaoId()));

        Manutencao salva = registrar(veiculo, tipo, dto.dataRealizacao(), dto.quilometragem(), dto.observacoes());
        return paraDTO(salva);
    }

    @Transactional
    public Manutencao registrar(Veiculo veiculo, TipoManutencao tipo, LocalDate dataRealizacao,
                                Integer quilometragem, String observacoes) {
        Integer kmAtual = historicoRepository.findTopByVeiculoIdOrderByDataRegistroDesc(veiculo.getId())
                .map(HistoricoQuilometragem::getQuilometragem)
                .orElse(0);

        if (quilometragem < kmAtual) {
            throw new QuilometragemInvalidaException(quilometragem, kmAtual);
        }

        Manutencao manutencao = new Manutencao();
        manutencao.setVeiculo(veiculo);
        manutencao.setTipoManutencao(tipo);
        manutencao.setDataRealizacao(dataRealizacao);
        manutencao.setQuilometragem(quilometragem);
        manutencao.setObservacoes(observacoes);
        Manutencao salva = manutencaoRepository.save(manutencao);

        HistoricoQuilometragem historico = new HistoricoQuilometragem();
        historico.setVeiculo(veiculo);
        historico.setQuilometragem(quilometragem);
        historico.setDataRegistro(LocalDateTime.now());
        historicoRepository.save(historico);

        return salva;
    }

    public List<ManutencaoRespostaDTO> listarPorVeiculo(Long veiculoId) {
        return manutencaoRepository.findByVeiculoIdOrderByDataRealizacaoDesc(veiculoId).stream()
                .map(this::paraDTO)
                .toList();
    }

    public ManutencaoRespostaDTO paraDTO(Manutencao m) {
        return new ManutencaoRespostaDTO(
                m.getId(),
                m.getVeiculo().getId(),
                m.getTipoManutencao().getNome(),
                m.getDataRealizacao(),
                m.getQuilometragem(),
                m.getObservacoes()
        );
    }
}