package br.com.nextgen.DTO;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String senha,
        String fotoPerfil
) {}