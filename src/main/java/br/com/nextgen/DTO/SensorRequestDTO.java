package br.com.nextgen.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SensorRequestDTO(
        @NotBlank String ip,
        @NotBlank String tipo, // "TEMPERATURA" ou "UMIDADE"
        @NotNull UUID idTalhao
) {}