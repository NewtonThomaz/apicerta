package br.com.nextgen.Repository;

import br.com.nextgen.Entity.Talhao;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalhaoRepository extends JpaRepository<Talhao, UUID> {
    List<Talhao> findAllByAtivoTrue();
    List<Talhao> findAllByAtivoFalse();
}