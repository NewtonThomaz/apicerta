package br.com.nextgen.Service;

import br.com.nextgen.DTO.TalhaoDetalhadoDTO;
import br.com.nextgen.DTO.TalhaoRequestDTO;
import br.com.nextgen.Entity.*;
import br.com.nextgen.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service; // Importante!
import org.springframework.transaction.annotation.Transactional; // Importante!

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TalhaoService {

    private final TalhaoRepository talhaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CulturaRepository culturaRepository;
    private final OperacaoRepository operacaoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public TalhaoService(TalhaoRepository talhaoRepository,
                         UsuarioRepository usuarioRepository,
                         CulturaRepository culturaRepository,
                         OperacaoRepository operacaoRepository,
                         ColaboradorRepository colaboradorRepository) {
        this.talhaoRepository = talhaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.culturaRepository = culturaRepository;
        this.operacaoRepository = operacaoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }
    @Transactional(readOnly = true)
    public TalhaoDetalhadoDTO buscarDetalhadoPorId(UUID id) {
        Optional<Talhao> talhaoOpt = talhaoRepository.findById(id);

        if (talhaoOpt.isPresent()) {
            Talhao talhao = talhaoOpt.get();
            // A conversão acontece aqui, acessando as listas dentro da transação
            return TalhaoDetalhadoDTO.fromEntity(talhao);
        }
        return null;
    }

    public Talhao converterDtoParaEntity(TalhaoRequestDTO dto) {
        Talhao talhao = new Talhao();
        talhao.setNome(dto.nome());
        talhao.setDescricao(dto.descricao());
        talhao.setTamanho(dto.tamanho());
        talhao.setMedida(dto.medida());
        talhao.setAtivo(true);

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        talhao.setUsuario(usuario);

        if (dto.idsCulturas() != null) {
            talhao.setCulturas(culturaRepository.findAllById(dto.idsCulturas()));
        }
        if (dto.idsOperacoes() != null) {
            talhao.setOperacoes(operacaoRepository.findAllById(dto.idsOperacoes()));
        }
        if (dto.idsColaboradores() != null) {
            talhao.setColaboradores(colaboradorRepository.findAllById(dto.idsColaboradores()));
        }

        return talhao;
    }

    public Talhao salvarViaDTO(TalhaoRequestDTO dto) {
        Talhao talhao = converterDtoParaEntity(dto);
        return talhaoRepository.save(talhao);
    }

    public Talhao atualizarViaDTO(UUID id, TalhaoRequestDTO dto) {
        Optional<Talhao> existenteOpt = talhaoRepository.findById(id);
        if (existenteOpt.isPresent()) {
            Talhao talhao = converterDtoParaEntity(dto);
            talhao.setId(id);
            talhao.setAtivo(existenteOpt.get().getAtivo());
            return talhaoRepository.save(talhao);
        }
        return null;
    }

    public List<Talhao> listarAtivos() { return talhaoRepository.findAllByAtivoTrue(); }
    public List<Talhao> listarInativos() { return talhaoRepository.findAllByAtivoFalse(); }
    public List<Talhao> listarTodos() { return talhaoRepository.findAll(); }

    public Talhao buscarPorId(UUID id) {
        return talhaoRepository.findById(id).orElse(null);
    }

    public Boolean deletar(UUID id) {
        if (talhaoRepository.existsById(id)) {
            talhaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}