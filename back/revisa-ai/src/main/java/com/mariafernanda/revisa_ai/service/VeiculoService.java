package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.*;
import com.mariafernanda.revisa_ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoQuilometragemRepository historicoRepository;

    @Transactional
    public VeiculoRespostaDTO cadastrar(Long usuarioId, VeiculoCadastroDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        if (veiculoRepository.findByPlaca(dto.placa()).isPresent()) {
            throw new PlacaJaCadastradaException(dto.placa());
        }

        Veiculo veiculo = new Veiculo();
        veiculo.setUsuario(usuario);
        veiculo.setNome(dto.nome());
        veiculo.setPlaca(dto.placa());
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setAno(dto.ano());
        Veiculo salvo = veiculoRepository.save(veiculo);

        HistoricoQuilometragem historico = new HistoricoQuilometragem();
        historico.setVeiculo(salvo);
        historico.setQuilometragem(dto.quilometragemInicial());
        historico.setDataRegistro(LocalDateTime.now());
        historicoRepository.save(historico);

        return new VeiculoRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getPlaca(), salvo.getMarca(),
                salvo.getModelo(), salvo.getAno(), dto.quilometragemInicial());
    }

    public VeiculoRespostaDTO alterar(Long usuarioId, Long id, VeiculoAlteracaoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        validarPropriedade(veiculo, usuarioId);

        veiculo.setNome(dto.nome());
        veiculo.setPlaca(dto.placa());
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setAno(dto.ano());
        Veiculo salvo = veiculoRepository.save(veiculo);

        Integer kmAtual = buscarQuilometragemAtual(id);
        return new VeiculoRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getPlaca(), salvo.getMarca(),
                salvo.getModelo(), salvo.getAno(), kmAtual);
    }

    public void deletar(Long usuarioId, Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));

        validarPropriedade(veiculo, usuarioId);

        veiculoRepository.delete(veiculo);
    }

    public List<VeiculoRespostaDTO> listarPorUsuario(Long usuarioIdAutenticado, Long usuarioIdConsultado) {
        if (!usuarioIdAutenticado.equals(usuarioIdConsultado)) {
            throw new AcessoNegadoException();
        }

        return veiculoRepository.findByUsuarioId(usuarioIdConsultado).stream()
                .map(v -> new VeiculoRespostaDTO(v.getId(), v.getNome(), v.getPlaca(), v.getMarca(),
                        v.getModelo(), v.getAno(), buscarQuilometragemAtual(v.getId())))
                .toList();
    }

    public void validarPropriedade(Veiculo veiculo, Long usuarioId) {
        if (!veiculo.getUsuario().getId().equals(usuarioId)) {
            throw new AcessoNegadoException();
        }
    }

    private Integer buscarQuilometragemAtual(Long veiculoId) {
        return historicoRepository.findTopByVeiculoIdOrderByDataRegistroDesc(veiculoId)
                .map(HistoricoQuilometragem::getQuilometragem)
                .orElse(0);
    }
}