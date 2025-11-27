package br.com.nextgen.Service;

import br.com.nextgen.Entity.Colaborador;
import br.com.nextgen.Repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public List<Colaborador> listarTodos() {
        return colaboradorRepository.findAll();
    }

    public Colaborador buscarPorId(UUID id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        return colaborador.orElse(null);
    }

    public Colaborador salvar(Colaborador colaborador) {
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