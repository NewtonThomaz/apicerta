package br.com.nextgen.Controller;

import br.com.nextgen.DTO.TalhaoDetalhadoDTO;
import br.com.nextgen.DTO.TalhaoRequestDTO;
import br.com.nextgen.DTO.TalhaoResponseDTO;
import br.com.nextgen.Entity.Talhao;
import br.com.nextgen.Service.TalhaoService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;

    public TalhaoController(TalhaoService talhaoService) {
        this.talhaoService = talhaoService;
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<TalhaoDetalhadoDTO> getTalhaoDetalhado(@PathVariable UUID id) {
        TalhaoDetalhadoDTO dto = talhaoService.buscarDetalhadoPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalhaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        Talhao talhao = talhaoService.buscarPorId(id);
        if (talhao != null) {
            return ResponseEntity.ok(new TalhaoResponseDTO(talhao));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<TalhaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(talhaoService.listarTodos().stream()
                .map(TalhaoResponseDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<TalhaoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(talhaoService.listarAtivos().stream()
                .map(TalhaoResponseDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/inativos")
    public ResponseEntity<List<TalhaoResponseDTO>> listarInativos() {
        return ResponseEntity.ok(talhaoService.listarInativos().stream()
                .map(TalhaoResponseDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("/")
    public ResponseEntity<TalhaoResponseDTO> criarTalhao(@RequestBody @Valid TalhaoRequestDTO dto) {
        Talhao novoTalhao = talhaoService.salvarViaDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TalhaoResponseDTO(novoTalhao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalhaoResponseDTO> atualizarTalhao(@PathVariable UUID id, @RequestBody @Valid TalhaoRequestDTO dto) {
        Talhao talhaoAtualizado = talhaoService.atualizarViaDTO(id, dto);
        if (talhaoAtualizado != null) {
            return ResponseEntity.ok(new TalhaoResponseDTO(talhaoAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTalhao(@PathVariable UUID id) {
        Boolean deletado = talhaoService.deletar(id);
        if (deletado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
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