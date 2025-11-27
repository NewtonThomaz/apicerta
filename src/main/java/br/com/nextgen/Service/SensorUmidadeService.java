package br.com.nextgen.Service;

import br.com.nextgen.DTO.SensorDTO;
import br.com.nextgen.DTO.SensorRequestDTO;
import br.com.nextgen.Entity.SensorUmidade;
import br.com.nextgen.Repository.SensorUmidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorUmidadeService {

    private final SensorUmidadeRepository sensorRepository;

    public SensorUmidadeService(SensorUmidadeRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<SensorDTO> listarTodos() {
        return sensorRepository.findAll().stream()
                .map(SensorDTO::new) // Converte para o DTO unificado
                .collect(Collectors.toList());
    }

    public SensorUmidade buscarPorId(UUID id) {
        Optional<SensorUmidade> sensor = sensorRepository.findById(id);
        return sensor.orElse(null);
    }

    public SensorDTO salvar(SensorRequestDTO dto) {
        // Lógica simplificada: Cria um novo sensor com o IP fornecido.
        // Futuramente, você pode buscar se já existe um sensor para esse talhão e atualizar.
        SensorUmidade sensor = new SensorUmidade();
        sensor.setIp(dto.ip());

        SensorUmidade salvo = sensorRepository.save(sensor);
        return new SensorDTO(salvo);
    }

    public SensorUmidade atualizar(UUID id, SensorUmidade sensorAtualizado) {
        Optional<SensorUmidade> sensorExistente = sensorRepository.findById(id);
        if (sensorExistente.isPresent()) {
            sensorAtualizado.setId(id);
            return sensorRepository.save(sensorAtualizado);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<SensorUmidade> sensorExistente = sensorRepository.findById(id);
        if (sensorExistente.isPresent()) {
            sensorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}