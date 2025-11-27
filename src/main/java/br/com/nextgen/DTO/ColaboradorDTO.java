package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Colaborador;
import java.util.UUID;

public record ColaboradorDTO(UUID id, String email, String permissao) {
    public ColaboradorDTO(Colaborador c) {
        this(c.getId(), c.getUsuario().getEmail(), c.getPermissao().toString());
    }
}