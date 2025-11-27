package br.com.nextgen.Controller;

import br.com.nextgen.DTO.SensorDTO;
import br.com.nextgen.DTO.SensorRequestDTO;
import br.com.nextgen.Entity.SensorUmidade;
import br.com.nextgen.Service.SensorUmidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sensoresumidade")
public class SensorUmidadeController {

    private final SensorUmidadeService sensorService;

    public SensorUmidadeController(SensorUmidadeService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorUmidade> buscarPorId(@PathVariable UUID id) {
        SensorUmidade sensor = sensorService.buscarPorId(id);
        if (sensor != null) {
            return ResponseEntity.ok(sensor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<SensorDTO>> listarTodos() {
        return ResponseEntity.ok(sensorService.listarTodos());
    }

    @PostMapping("/")
    public ResponseEntity<SensorDTO> criarOuAtualizar(@RequestBody @Valid SensorRequestDTO dto) {
        return ResponseEntity.ok(sensorService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorUmidade> atualizarSensor(@PathVariable UUID id, @RequestBody @Valid SensorUmidade sensor) {
        SensorUmidade sensorAtualizado = sensorService.atualizar(id, sensor);
        if (sensorAtualizado != null) {
            return ResponseEntity.ok(sensorAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSensor(@PathVariable UUID id) {
        Boolean deletado = sensorService.deletar(id);
        if (deletado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}