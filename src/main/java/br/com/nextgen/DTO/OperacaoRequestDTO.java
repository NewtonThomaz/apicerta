package br.com.nextgen.DTO;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OperacaoRequestDTO(

        @NotNull(message = "O ID do talhão é obrigatório")
        UUID idTalhao,

        @NotBlank(message = "A descrição da operação é obrigatória")
        String operacao,

        @NotBlank(message = "O nome do agente/ator é obrigatório")
        String agente,

        @NotNull(message = "A data e hora são obrigatórias")
        LocalDateTime dataHora
) {
}