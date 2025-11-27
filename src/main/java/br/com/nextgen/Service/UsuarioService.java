package br.com.nextgen.Service;

import br.com.nextgen.Entity.Usuario;
import br.com.nextgen.Repository.UsuarioRepository;
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
    // Injeção de dependência do Encoder via construtor pode criar dependência circular,
    // então em alguns casos usamos @Lazy ou injetamos na chamada.
    // Mas com a config atual, deve funcionar se o SecurityConfig estiver separado.
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método exigido pelo Spring Security para login
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

    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            usuarioAtualizado.setId(id);

            // Se a senha foi alterada, criptografa novamente. Se vier vazia, mantém a antiga.
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuarioAtualizado.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            } else {
                usuarioAtualizado.setSenha(usuarioExistente.get().getSenha());
            }

            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}