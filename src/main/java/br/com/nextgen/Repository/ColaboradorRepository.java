package br.com.nextgen.Repository;

import br.com.nextgen.Entity.Colaborador;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {
}