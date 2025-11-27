package br.com.nextgen.Repository;

import br.com.nextgen.Entity.Operacao;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoRepository extends JpaRepository<Operacao, UUID> {
}