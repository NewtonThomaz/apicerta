package br.com.nextgen.Service;

import br.com.nextgen.Entity.LeituraUmidade;
import br.com.nextgen.Repository.LeituraUmidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeituraUmidadeService {

    private final LeituraUmidadeRepository leituraRepository;

    public LeituraUmidadeService(LeituraUmidadeRepository leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    public List<LeituraUmidade> listarTodos() {
        return leituraRepository.findAll();
    }

    public LeituraUmidade buscarPorId(UUID id) {
        Optional<LeituraUmidade> leitura = leituraRepository.findById(id);
        return leitura.orElse(null);
    }

    public LeituraUmidade salvar(LeituraUmidade leitura) {
        return leituraRepository.save(leitura);
    }

    public LeituraUmidade atualizar(UUID id, LeituraUmidade leituraAtualizada) {
        Optional<LeituraUmidade> leituraExistente = leituraRepository.findById(id);
        if (leituraExistente.isPresent()) {
            leituraAtualizada.setId(id);
            return leituraRepository.save(leituraAtualizada);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<LeituraUmidade> leituraExistente = leituraRepository.findById(id);
        if (leituraExistente.isPresent()) {
            leituraRepository.deleteById(id);
            return true;
        }
        return false;
    }
}