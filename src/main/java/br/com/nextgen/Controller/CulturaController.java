package br.com.nextgen.Controller;

import br.com.nextgen.Entity.Cultura;
import br.com.nextgen.Service.CulturaService;
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
@RequestMapping("/culturas")
public class CulturaController {

    private final CulturaService culturaService;

    public CulturaController(CulturaService culturaService) {
        this.culturaService = culturaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cultura> buscarPorId(@PathVariable UUID id) {
        Cultura cultura = culturaService.buscarPorId(id);
        if (cultura != null) {
            return ResponseEntity.ok(cultura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Cultura>> listarTodos() {
        List<Cultura> culturas = culturaService.listarTodos();
        return ResponseEntity.ok(culturas);
    }

    @PostMapping("/")
    public ResponseEntity<Cultura> criarCultura(@RequestBody @Valid Cultura cultura) {
        Cultura novaCultura = culturaService.salvar(cultura);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCultura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultura> atualizarCultura(@PathVariable UUID id, @RequestBody @Valid Cultura cultura) {
        Cultura culturaAtualizada = culturaService.atualizar(id, cultura);
        if (culturaAtualizada != null) {
            return ResponseEntity.ok(culturaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCultura(@PathVariable UUID id) {
        Boolean deletado = culturaService.deletar(id);
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