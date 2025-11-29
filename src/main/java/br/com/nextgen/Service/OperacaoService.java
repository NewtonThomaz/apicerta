package br.com.nextgen.Service;

import br.com.nextgen.DTO.OperacaoRequestDTO; // <--- Importante: Use o RequestDTO aqui
import br.com.nextgen.Entity.Operacao;
import br.com.nextgen.Entity.Talhao;
import br.com.nextgen.Repository.OperacaoRepository;
import br.com.nextgen.Repository.TalhaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperacaoService {

    private final OperacaoRepository operacaoRepository;
    private final TalhaoRepository talhaoRepository;

    public OperacaoService(OperacaoRepository operacaoRepository, TalhaoRepository talhaoRepository) {
        this.operacaoRepository = operacaoRepository;
        this.talhaoRepository = talhaoRepository;
    }

    public List<Operacao> buscarPorTalhao(UUID idTalhao) {
        return operacaoRepository.findByTalhaoId(idTalhao);
    }

    public List<Operacao> listarTodos() {
        return operacaoRepository.findAll();
    }

    public Operacao buscarPorId(UUID id) {
        Optional<Operacao> operacao = operacaoRepository.findById(id);
        return operacao.orElse(null);
    }

    public Operacao salvar(OperacaoRequestDTO dto) {
        Talhao talhao = talhaoRepository.findById(dto.idTalhao())
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + dto.idTalhao()));

        Operacao operacao = new Operacao();
        operacao.setTalhao(talhao);
        operacao.setOperacao(dto.operacao());
        operacao.setAgente(dto.agente());
        operacao.setDataHora(dto.dataHora());

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