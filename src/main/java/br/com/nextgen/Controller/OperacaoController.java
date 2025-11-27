package br.com.nextgen.Controller;

import br.com.nextgen.Entity.Operacao;
import br.com.nextgen.Service.OperacaoService;
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
@RequestMapping("/operacoes")
public class OperacaoController {

    private final OperacaoService operacaoService;

    public OperacaoController(OperacaoService operacaoService) {
        this.operacaoService = operacaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operacao> buscarPorId(@PathVariable UUID id) {
        Operacao operacao = operacaoService.buscarPorId(id);
        if (operacao != null) {
            return ResponseEntity.ok(operacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Operacao>> listarTodos() {
        List<Operacao> operacoes = operacaoService.listarTodos();
        return ResponseEntity.ok(operacoes);
    }

    @PostMapping("/")
    public ResponseEntity<Operacao> criarOperacao(@RequestBody @Valid Operacao operacao) {
        Operacao novaOperacao = operacaoService.salvar(operacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOperacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operacao> atualizarOperacao(@PathVariable UUID id, @RequestBody @Valid Operacao operacao) {
        Operacao operacaoAtualizada = operacaoService.atualizar(id, operacao);
        if (operacaoAtualizada != null) {
            return ResponseEntity.ok(operacaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOperacao(@PathVariable UUID id) {
        Boolean deletado = operacaoService.deletar(id);
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