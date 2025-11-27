package br.com.nextgen.Service;

import br.com.nextgen.Repository.TalhaoRepository;
import br.com.nextgen.Repository.UsuarioRepository;
import br.com.nextgen.Entity.Permissao;
import br.com.nextgen.Entity.Usuario;
import br.com.nextgen.Entity.Talhao;
import br.com.nextgen.DTO.ColaboradorRequestDTO;
import br.com.nextgen.Entity.Colaborador;
import br.com.nextgen.Repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TalhaoRepository talhaoRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository, UsuarioRepository usuarioRepository, TalhaoRepository talhaoRepository) {
        this.colaboradorRepository = colaboradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.talhaoRepository = talhaoRepository;
    }

    public List<Colaborador> listarTodos() {
        return colaboradorRepository.findAll();
    }

    public Colaborador buscarPorId(UUID id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        return colaborador.orElse(null);
    }

    public Colaborador salvar(ColaboradorRequestDTO dto) {
        // 1. Busca quem será o colaborador pelo E-mail (que veio do front)
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + dto.email()));

        // 2. Busca o talhão pelo ID (que veio do front)
        Talhao talhao = talhaoRepository.findById(dto.idTalhao())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado"));

        // 3. Monta a entidade
        Colaborador colaborador = new Colaborador();
        colaborador.setUsuario(usuario);
        colaborador.setTalhao(talhao);
        // Converte a String do DTO para o Enum do banco
        colaborador.setPermissao(Permissao.valueOf(dto.permissao()));

        return colaboradorRepository.save(colaborador);
    }

    public Colaborador atualizar(UUID id, Colaborador colaboradorAtualizado) {
        Optional<Colaborador> colaboradorExistente = colaboradorRepository.findById(id);
        if (colaboradorExistente.isPresent()) {
            colaboradorAtualizado.setId(id);
            return colaboradorRepository.save(colaboradorAtualizado);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<Colaborador> colaboradorExistente = colaboradorRepository.findById(id);
        if (colaboradorExistente.isPresent()) {
            colaboradorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}