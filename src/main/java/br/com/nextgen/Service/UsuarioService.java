package br.com.nextgen.Service;

import br.com.nextgen.DTO.UsuarioUpdateDTO;
import br.com.nextgen.Entity.Usuario;
import br.com.nextgen.Repository.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(UUID id, UsuarioUpdateDTO dados) {
        Usuario usuario = buscarPorId(id);
        if (usuario == null) return null;
        // Só atualiza se o dado foi enviado (não é nulo nem vazio)
        if (dados.nome() != null && !dados.nome().isBlank()) {
            usuario.setNome(dados.nome());
        }
        if (dados.email() != null && !dados.email().isBlank()) {
            usuario.setEmail(dados.email());
        }
        if (dados.senha() != null && !dados.senha().isBlank()) {
            usuario.setSenha(dados.senha());
        }

        if (dados.fotoPerfil() != null) {
            usuario.setFotoPerfil(dados.fotoPerfil());
        }

        return usuarioRepository.save(usuario);
    }

    public Boolean deletar(UUID id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario atualizarFoto(UUID id, MultipartFile file) {
        Usuario usuario = buscarPorId(id);
        if (usuario == null) throw new RuntimeException("Usuário não encontrado");

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();

        usuario.setFotoPerfil(fileDownloadUri);
        return usuarioRepository.save(usuario);
    }
}