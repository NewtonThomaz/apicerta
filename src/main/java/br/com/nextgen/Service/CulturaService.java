package br.com.nextgen.Service;

import br.com.nextgen.Entity.Cultura;
import br.com.nextgen.Repository.CulturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CulturaService {

    private final CulturaRepository culturaRepository;

    public CulturaService(CulturaRepository culturaRepository) {
        this.culturaRepository = culturaRepository;
    }

    public List<Cultura> listarTodos() {
        return culturaRepository.findAll();
    }

    public Cultura buscarPorId(UUID id) {
        Optional<Cultura> cultura = culturaRepository.findById(id);
        return cultura.orElse(null);
    }

    public Cultura salvar(Cultura cultura) {
        return culturaRepository.save(cultura);
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