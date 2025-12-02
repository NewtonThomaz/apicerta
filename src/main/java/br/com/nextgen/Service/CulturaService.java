package br.com.nextgen.Service;

import br.com.nextgen.Entity.Cultura;
import br.com.nextgen.Entity.Talhao;
import br.com.nextgen.Repository.CulturaRepository;
import br.com.nextgen.Repository.TalhaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CulturaService {

    private final CulturaRepository culturaRepository;
    private final TalhaoRepository talhaoRepository;

    public CulturaService(CulturaRepository culturaRepository, TalhaoRepository talhaoRepository) {
        this.culturaRepository = culturaRepository;
        this.talhaoRepository = talhaoRepository;
    }

    public List<Cultura> listarTodos() {
        return culturaRepository.findAll();
    }

    public Cultura buscarPorId(UUID id) {
        Optional<Cultura> cultura = culturaRepository.findById(id);
        return cultura.orElse(null);
    }

    @Transactional
    public Cultura salvar(Cultura cultura) {
        Cultura culturaSalva = culturaRepository.save(cultura);
        if (cultura.getTalhoes() != null && !cultura.getTalhoes().isEmpty()) {
            for (Talhao t : cultura.getTalhoes()) {
                Talhao talhaoExistente = talhaoRepository.findById(t.getId())
                        .orElseThrow(() -> new RuntimeException("Talhão não encontrado"));
                talhaoExistente.getCulturas().add(culturaSalva);
                talhaoRepository.save(talhaoExistente);
            }
            culturaSalva.setTalhoes(cultura.getTalhoes());
        }
        return culturaSalva;
    }

    public Cultura atualizar(UUID id, Cultura culturaAtualizada) {
        Optional<Cultura> culturaExistente = culturaRepository.findById(id);
        if (culturaExistente.isPresent()) {
            culturaAtualizada.setId(id);
            return culturaRepository.save(culturaAtualizada);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<Cultura> culturaExistente = culturaRepository.findById(id);
        if (culturaExistente.isPresent()) {
            culturaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}