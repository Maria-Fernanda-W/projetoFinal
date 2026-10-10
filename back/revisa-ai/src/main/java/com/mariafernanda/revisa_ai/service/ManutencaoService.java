package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.*;
import com.mariafernanda.revisa_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        Manutencao salva = registrar(veiculo, tipo, dto.nome(), dto.dataRealizacao(),
                dto.valor(), dto.quilometragem(), dto.localRealizado(), dto.observacoes());
        return paraDTO(salva);
    }

    @Transactional
    public Manutencao registrar(Veiculo veiculo, TipoManutencao tipo, String nome, LocalDate dataRealizacao,
                                BigDecimal valor, Integer quilometragem, String localRealizado, String observacoes) {
        if (quilometragem != null) {
            Integer kmAtual = historicoRepository.findTopByVeiculoIdOrderByDataRegistroDesc(veiculo.getId())
                    .map(HistoricoQuilometragem::getQuilometragem)
                    .orElse(0);

            if (quilometragem < kmAtual) {
                throw new QuilometragemInvalidaException(quilometragem, kmAtual);
            }
        }

        Manutencao manutencao = new Manutencao();
        manutencao.setVeiculo(veiculo);
        manutencao.setTipoManutencao(tipo);
        manutencao.setNome(nome);
        manutencao.setDataRealizacao(dataRealizacao);
        manutencao.setValor(valor);
        manutencao.setQuilometragem(quilometragem);
        manutencao.setLocalRealizado(localRealizado);
        manutencao.setObservacoes(observacoes);
        Manutencao salva = manutencaoRepository.save(manutencao);

        if (quilometragem != null) {
            HistoricoQuilometragem historico = new HistoricoQuilometragem();
            historico.setVeiculo(veiculo);
            historico.setQuilometragem(quilometragem);
            historico.setDataRegistro(LocalDateTime.now());
            historicoRepository.save(historico);
        }

        return salva;
    }

    public ManutencaoRespostaDTO alterar(Long usuarioId, Long id, ManutencaoAlteracaoDTO dto) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ManutencaoNaoEncontradaException(id));

        veiculoService.validarPropriedade(manutencao.getVeiculo(), usuarioId);

        TipoManutencao tipo = tipoManutencaoRepository.findById(dto.tipoManutencaoId())
                .orElseThrow(() -> new TipoManutencaoNaoEncontradoException(dto.tipoManutencaoId()));

        manutencao.setTipoManutencao(tipo);
        manutencao.setNome(dto.nome());
        manutencao.setDataRealizacao(dto.dataRealizacao());
        manutencao.setValor(dto.valor());
        manutencao.setLocalRealizado(dto.localRealizado());
        manutencao.setObservacoes(dto.observacoes());

        return paraDTO(manutencaoRepository.save(manutencao));
    }

    public void deletar(Long usuarioId, Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new ManutencaoNaoEncontradaException(id));

        veiculoService.validarPropriedade(manutencao.getVeiculo(), usuarioId);

        manutencaoRepository.delete(manutencao);
    }

    public List<ManutencaoRespostaDTO> listarPorVeiculo(Long veiculoId) {
        return manutencaoRepository.findByVeiculoIdOrderByDataRealizacaoDesc(veiculoId).stream()
                .map(this::paraDTO)
                .toList();
    }

    public List<ManutencaoRespostaDTO> listarPorUsuario(Long usuarioIdAutenticado, Long usuarioIdConsultado) {
        if (!usuarioIdAutenticado.equals(usuarioIdConsultado)) {
            throw new AcessoNegadoException();
        }

        return manutencaoRepository.findByVeiculoUsuarioIdOrderByDataRealizacaoDesc(usuarioIdConsultado).stream()
                .map(this::paraDTO)
                .toList();
    }

    public ManutencaoRespostaDTO paraDTO(Manutencao m) {
        return new ManutencaoRespostaDTO(
                m.getId(),
                m.getVeiculo().getId(),
                m.getVeiculo().getNome(),
                m.getTipoManutencao().getNome(),
                m.getNome(),
                m.getDataRealizacao(),
                m.getValor(),
                m.getQuilometragem(),
                m.getLocalRealizado(),
                m.getObservacoes()
        );
    }
}