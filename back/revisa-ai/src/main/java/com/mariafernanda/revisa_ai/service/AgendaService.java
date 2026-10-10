package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.*;
import com.mariafernanda.revisa_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final VeiculoRepository veiculoRepository;
    private final TipoManutencaoRepository tipoManutencaoRepository;
    private final ManutencaoService manutencaoService;
    private final VeiculoService veiculoService;

    public AgendaRespostaDTO planejar(Long usuarioId, AgendaCadastroDTO dto) {
        if (dto.dataAlvo() == null && dto.quilometragemAlvo() == null) {
            throw new AlvoAgendaObrigatorioException();
        }

        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException(dto.veiculoId()));

        veiculoService.validarPropriedade(veiculo, usuarioId);

        TipoManutencao tipo = tipoManutencaoRepository.findById(dto.tipoManutencaoId())
                .orElseThrow(() -> new TipoManutencaoNaoEncontradoException(dto.tipoManutencaoId()));

        Agenda agenda = new Agenda();
        agenda.setVeiculo(veiculo);
        agenda.setTipoManutencao(tipo);
        agenda.setDataAlvo(dto.dataAlvo());
        agenda.setQuilometragemAlvo(dto.quilometragemAlvo());
        agenda.setStatus(StatusAgenda.PENDENTE);

        return paraDTO(agendaRepository.save(agenda));
    }

    @Transactional
    public AgendaRespostaDTO converter(Long usuarioId, Long id, AgendaConversaoDTO dto) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNaoEncontradaException(id));

        veiculoService.validarPropriedade(agenda.getVeiculo(), usuarioId);

        if (agenda.getStatus() == StatusAgenda.CONCLUIDA) {
            throw new AgendaJaConcluidaException(id);
        }

        Manutencao manutencao = manutencaoService.registrar(
                agenda.getVeiculo(), agenda.getTipoManutencao(), dto.nome(), dto.dataRealizacao(),
                dto.valor(), dto.quilometragem(), dto.localRealizado(), dto.observacoes());

        agenda.setManutencao(manutencao);
        agenda.setStatus(StatusAgenda.CONCLUIDA);

        return paraDTO(agendaRepository.save(agenda));
    }

    public List<AgendaRespostaDTO> listarPendentesPorVeiculo(Long veiculoId) {
        return agendaRepository.findByVeiculoIdAndStatus(veiculoId, StatusAgenda.PENDENTE).stream()
                .map(this::paraDTO)
                .toList();
    }

    private AgendaRespostaDTO paraDTO(Agenda a) {
        return new AgendaRespostaDTO(
                a.getId(),
                a.getVeiculo().getId(),
                a.getTipoManutencao().getNome(),
                a.getDataAlvo(),
                a.getQuilometragemAlvo(),
                a.getStatus().name(),
                a.getManutencao() != null ? a.getManutencao().getId() : null
        );
    }
}