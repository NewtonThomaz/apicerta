package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Operacao;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperacaoDTO(UUID id, String operacao, String agente, LocalDateTime dataHora) {
    public OperacaoDTO(Operacao o) {
        this(o.getId(), o.getOperacao(), o.getAgente(), o.getDataHora());
    }
}