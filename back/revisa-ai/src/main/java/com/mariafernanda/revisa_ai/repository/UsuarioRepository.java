package com.mariafernanda.revisa_ai.repository;

import com.mariafernanda.revisa_ai.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Object> findByEmail(@NotBlank @Email String email);
}