package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Usuario;
import java.util.UUID;

public record UsuarioDTO(UUID id, String nome, String email, String fotoPerfil) {
    public UsuarioDTO(Usuario u) {
        this(u.getId(), u.getNome(), u.getEmail(), u.getFotoPerfil());
    }
}