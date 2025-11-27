package br.com.nextgen.Controller;

import br.com.nextgen.Entity.LeituraTemperatura;
import br.com.nextgen.Service.LeituraTemperaturaService;
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
@RequestMapping("/leiturastemperatura")
public class LeituraTemperaturaController {

    private final LeituraTemperaturaService leituraService;

    public LeituraTemperaturaController(LeituraTemperaturaService leituraService) {
        this.leituraService = leituraService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraTemperatura> buscarPorId(@PathVariable UUID id) {
        LeituraTemperatura leitura = leituraService.buscarPorId(id);
        if (leitura != null) {
            return ResponseEntity.ok(leitura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<LeituraTemperatura>> listarTodos() {
        List<LeituraTemperatura> leituras = leituraService.listarTodos();
        return ResponseEntity.ok(leituras);
    }

    @PostMapping("/")
    public ResponseEntity<LeituraTemperatura> criarLeitura(@RequestBody @Valid LeituraTemperatura leitura) {
        LeituraTemperatura novaLeitura = leituraService.salvar(leitura);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLeitura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraTemperatura> atualizarLeitura(@PathVariable UUID id, @RequestBody @Valid LeituraTemperatura leitura) {
        LeituraTemperatura leituraAtualizada = leituraService.atualizar(id, leitura);
        if (leituraAtualizada != null) {
            return ResponseEntity.ok(leituraAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLeitura(@PathVariable UUID id) {
        Boolean deletado = leituraService.deletar(id);
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