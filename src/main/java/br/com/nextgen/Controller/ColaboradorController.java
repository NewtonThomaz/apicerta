package br.com.nextgen.Controller;

import br.com.nextgen.Entity.Colaborador;
import br.com.nextgen.Service.ColaboradorService;
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
@RequestMapping("/colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> buscarPorId(@PathVariable UUID id) {
        Colaborador colaborador = colaboradorService.buscarPorId(id);
        if (colaborador != null) {
            return ResponseEntity.ok(colaborador);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Colaborador>> listarTodos() {
        List<Colaborador> colaboradores = colaboradorService.listarTodos();
        return ResponseEntity.ok(colaboradores);
    }

    @PostMapping("/")
    public ResponseEntity<Colaborador> criarColaborador(@RequestBody @Valid Colaborador colaborador) {
        Colaborador novoColaborador = colaboradorService.salvar(colaborador);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoColaborador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> atualizarColaborador(@PathVariable UUID id, @RequestBody @Valid Colaborador colaborador) {
        Colaborador colaboradorAtualizado = colaboradorService.atualizar(id, colaborador);
        if (colaboradorAtualizado != null) {
            return ResponseEntity.ok(colaboradorAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarColaborador(@PathVariable UUID id) {
        Boolean deletado = colaboradorService.deletar(id);
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