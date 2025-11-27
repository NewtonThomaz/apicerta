package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Usuario;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String fotoPerfil
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getFotoPerfil());
    }
}