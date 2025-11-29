package br.com.nextgen.Repository;

import br.com.nextgen.Entity.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface OperacaoRepository extends JpaRepository<Operacao, UUID> {
    List<Operacao> findByTalhaoId(UUID idTalhao);
}