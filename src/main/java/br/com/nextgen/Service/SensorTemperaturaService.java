package br.com.nextgen.Service;

import br.com.nextgen.DTO.SensorDTO;
import br.com.nextgen.DTO.SensorRequestDTO;
import br.com.nextgen.Entity.SensorTemperatura;
import br.com.nextgen.Repository.SensorTemperaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorRepository;

    public SensorTemperaturaService(SensorTemperaturaRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<SensorDTO> listarTodos() {
        return sensorRepository.findAll().stream()
                .map(SensorDTO::new) // Converte para o DTO unificado
                .collect(Collectors.toList());
    }

    public SensorTemperatura buscarPorId(UUID id) {
        Optional<SensorTemperatura> sensor = sensorRepository.findById(id);
        return sensor.orElse(null);
    }

    public SensorDTO salvar(SensorRequestDTO dto) {
        // Lógica simplificada: Cria um novo sensor com o IP fornecido.
        // Futuramente, você pode buscar se já existe um sensor para esse talhão e atualizar.
        SensorTemperatura sensor = new SensorTemperatura();
        sensor.setIp(dto.ip());

        SensorTemperatura salvo = sensorRepository.save(sensor);
        return new SensorDTO(salvo);
    }

    public SensorTemperatura atualizar(UUID id, SensorTemperatura sensorAtualizado) {
        Optional<SensorTemperatura> sensorExistente = sensorRepository.findById(id);
        if (sensorExistente.isPresent()) {
            sensorAtualizado.setId(id);
            return sensorRepository.save(sensorAtualizado);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<SensorTemperatura> sensorExistente = sensorRepository.findById(id);
        if (sensorExistente.isPresent()) {
            sensorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}