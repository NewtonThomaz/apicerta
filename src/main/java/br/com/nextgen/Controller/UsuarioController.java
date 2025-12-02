package br.com.nextgen.Controller;

import br.com.nextgen.DTO.UsuarioDTO; // Importe o DTO
import java.util.stream.Collectors;
import br.com.nextgen.DTO.LoginRequestDTO;
import br.com.nextgen.DTO.LoginResponseDTO;
import br.com.nextgen.DTO.UsuarioRequestDTO;
import br.com.nextgen.DTO.UsuarioResponseDTO;
import br.com.nextgen.Entity.Usuario;
import br.com.nextgen.Service.UsuarioService;
import br.com.nextgen.Infra.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDTO> autenticar(@RequestBody @Valid LoginRequestDTO loginDTO) {
        // Cria o token de autenticação com os dados recebidos
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());

        // O Spring Security cuida de verificar a senha criptografada no banco
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se chegou aqui, a senha está correta. Gera o token JWT
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        // Conversão manual DTO -> Entity
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(usuarioDTO.senha()); // A criptografia ocorre no Service
        usuario.setFotoPerfil(usuarioDTO.fotoPerfil());

        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDTO(novoUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setFotoPerfil(usuarioDTO.fotoPerfil());

        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping(value = "/{id}/foto", consumes = "multipart/form-data")
    public ResponseEntity<UsuarioResponseDTO> uploadFotoPerfil(
            @PathVariable UUID id,
            @RequestParam("foto") MultipartFile file) {

        // Chama o método no service que salva o arquivo e atualiza o usuário
        // Nota: Certifique-se de ter criado o método 'atualizarFoto' no UsuarioService conforme o passo anterior
        Usuario usuarioAtualizado = usuarioService.atualizarFoto(id, file);

        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        Boolean deletado = usuarioService.deletar(id);
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