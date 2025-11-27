package br.com.nextgen.Repository;

import br.com.nextgen.Entity.LeituraUmidade;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeituraUmidadeRepository extends JpaRepository<LeituraUmidade, UUID> {
}