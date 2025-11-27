package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Medida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record TalhaoRequestDTO(
        @NotBlank String nome,
        String descricao,
        @NotNull Double tamanho,
        @NotNull UUID idUsuario,
        @NotNull Medida medida,
        List<UUID> idsCulturas,
        List<UUID> idsOperacoes,
        List<UUID> idsColaboradores
) {}