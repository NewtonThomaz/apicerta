package br.com.nextgen.DTO;

import br.com.nextgen.Entity.SensorTemperatura;
import br.com.nextgen.Entity.SensorUmidade;
import java.util.UUID;

public record SensorDTO(UUID id, String ip, String tipo) {
    // Construtor para Temperatura
    public SensorDTO(SensorTemperatura s) {
        this(s.getId(), s.getIp(), "TEMPERATURA");
    }

    // Construtor para Umidade
    public SensorDTO(SensorUmidade s) {
        this(s.getId(), s.getIp(), "UMIDADE");
    }
}