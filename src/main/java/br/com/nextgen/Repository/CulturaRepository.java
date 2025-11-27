package br.com.nextgen.Repository;

import br.com.nextgen.Entity.Cultura;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulturaRepository extends JpaRepository<Cultura, UUID> {
}