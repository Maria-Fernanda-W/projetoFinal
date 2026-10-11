package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.TipoManutencao;
import com.mariafernanda.revisa_ai.model.Usuario;
import com.mariafernanda.revisa_ai.repository.TipoManutencaoRepository;
import com.mariafernanda.revisa_ai.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoManutencaoService {

    private final TipoManutencaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public TipoManutencaoRespostaDTO cadastrar(Long usuarioId, TipoManutencaoDTO dto) {
        validarPeriodicidade(dto);

        boolean duplicado = repository.findByNomeAndUsuarioId(dto.nome(), usuarioId).isPresent();
        if (duplicado) {
            throw new TipoManutencaoJaCadastradoException(dto.nome());
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        TipoManutencao tipo = new TipoManutencao();
        tipo.setUsuario(usuario);
        preencher(tipo, dto);
        return paraDTO(repository.save(tipo));
    }

    public TipoManutencaoRespostaDTO alterar(Long usuarioId, Long id, TipoManutencaoDTO dto) {
        validarPeriodicidade(dto);
        TipoManutencao tipo = repository.findById(id)
                .orElseThrow(() -> new TipoManutencaoNaoEncontradoException(id));

        boolean ehPadrao = tipo.getUsuario() == null;

        if (ehPadrao) {
            return repository.findByOrigemPadraoIdAndUsuarioId(id, usuarioId)
                    .map(copiaExistente -> {
                        preencher(copiaExistente, dto);
                        return paraDTO(repository.save(copiaExistente));
                    })
                    .orElseGet(() -> criarCopiaPersonalizada(id, usuarioId, dto));
        }

        if (!tipo.getUsuario().getId().equals(usuarioId)) {
            throw new TipoManutencaoNaoPertenceAoUsuarioException(id);
        }

        preencher(tipo, dto);
        return paraDTO(repository.save(tipo));
    }

    public List<TipoManutencaoRespostaDTO> listarDisponiveisParaUsuario(Long usuarioId) {
        return repository.findDisponiveisParaUsuario(usuarioId).stream()
                .map(this::paraDTO)
                .toList();
    }

    public TipoManutencao buscarDisponivel(Long id, Long usuarioId) {
        TipoManutencao tipo = repository.findById(id)
                .orElseThrow(() -> new TipoManutencaoNaoEncontradoException(id));

        boolean ehPadrao = tipo.getUsuario() == null;
        boolean ehDoUsuario = !ehPadrao && tipo.getUsuario().getId().equals(usuarioId);

        if (!ehPadrao && !ehDoUsuario) {
            throw new TipoManutencaoNaoPertenceAoUsuarioException(id);
        }
        return tipo;
    }

    private TipoManutencaoRespostaDTO criarCopiaPersonalizada(Long origemPadraoId, Long usuarioId, TipoManutencaoDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        TipoManutencao copia = new TipoManutencao();
        copia.setUsuario(usuario);
        copia.setOrigemPadraoId(origemPadraoId);
        preencher(copia, dto);
        return paraDTO(repository.save(copia));
    }

    private void validarPeriodicidade(TipoManutencaoDTO dto) {
        if (dto.periodicidadeKm() == null && dto.periodicidadeMeses() == null) {
            throw new PeriodicidadeObrigatoriaException();
        }
    }

    private void preencher(TipoManutencao tipo, TipoManutencaoDTO dto) {
        tipo.setNome(dto.nome());
        tipo.setPeriodicidadeKm(dto.periodicidadeKm());
        tipo.setPeriodicidadeMeses(dto.periodicidadeMeses());
    }

    private TipoManutencaoRespostaDTO paraDTO(TipoManutencao t) {
        return new TipoManutencaoRespostaDTO(t.getId(), t.getNome(), t.getPeriodicidadeKm(),
                t.getPeriodicidadeMeses(), t.getUsuario() == null);
    }
}