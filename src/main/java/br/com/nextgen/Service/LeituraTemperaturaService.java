package br.com.nextgen.Service;

import br.com.nextgen.Entity.LeituraTemperatura;
import br.com.nextgen.Repository.LeituraTemperaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeituraTemperaturaService {

    private final LeituraTemperaturaRepository leituraRepository;

    public LeituraTemperaturaService(LeituraTemperaturaRepository leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    public List<LeituraTemperatura> listarTodos() {
        return leituraRepository.findAll();
    }

    public LeituraTemperatura buscarPorId(UUID id) {
        Optional<LeituraTemperatura> leitura = leituraRepository.findById(id);
        return leitura.orElse(null);
    }

    public LeituraTemperatura salvar(LeituraTemperatura leitura) {
        return leituraRepository.save(leitura);
    }

    public LeituraTemperatura atualizar(UUID id, LeituraTemperatura leituraAtualizada) {
        Optional<LeituraTemperatura> leituraExistente = leituraRepository.findById(id);
        if (leituraExistente.isPresent()) {
            leituraAtualizada.setId(id);
            return leituraRepository.save(leituraAtualizada);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<LeituraTemperatura> leituraExistente = leituraRepository.findById(id);
        if (leituraExistente.isPresent()) {
            leituraRepository.deleteById(id);
            return true;
        }
        return false;
    }
}