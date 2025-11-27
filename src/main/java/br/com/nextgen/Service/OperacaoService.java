package br.com.nextgen.Service;

import br.com.nextgen.Entity.Operacao;
import br.com.nextgen.Repository.OperacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperacaoService {

    private final OperacaoRepository operacaoRepository;

    public OperacaoService(OperacaoRepository operacaoRepository) {
        this.operacaoRepository = operacaoRepository;
    }

    public List<Operacao> listarTodos() {
        return operacaoRepository.findAll();
    }

    public Operacao buscarPorId(UUID id) {
        Optional<Operacao> operacao = operacaoRepository.findById(id);
        return operacao.orElse(null);
    }

    public Operacao salvar(Operacao operacao) {
        return operacaoRepository.save(operacao);
    }

    public Operacao atualizar(UUID id, Operacao operacaoAtualizada) {
        Optional<Operacao> operacaoExistente = operacaoRepository.findById(id);
        if (operacaoExistente.isPresent()) {
            operacaoAtualizada.setId(id);
            return operacaoRepository.save(operacaoAtualizada);
        }
        return null;
    }

    public Boolean deletar(UUID id) {
        Optional<Operacao> operacaoExistente = operacaoRepository.findById(id);
        if (operacaoExistente.isPresent()) {
            operacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}