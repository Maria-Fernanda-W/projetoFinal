package com.mariafernanda.revisa_ai.service;

import com.mariafernanda.revisa_ai.dto.*;
import com.mariafernanda.revisa_ai.exception.*;
import com.mariafernanda.revisa_ai.model.Usuario;
import com.mariafernanda.revisa_ai.repository.UsuarioRepository;
import com.mariafernanda.revisa_ai.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioRespostaDTO cadastrar(UsuarioCadastroDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario salvo = usuarioRepository.save(usuario);
        return new UsuarioRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getEmail());
    }

    public LoginRespostaDTO login(LoginDTO dto) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(dto.email())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtService.gerarToken(usuario.getId(), usuario.getEmail());
        UsuarioRespostaDTO respostaUsuario = new UsuarioRespostaDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
        return new LoginRespostaDTO(respostaUsuario, token);
    }
}