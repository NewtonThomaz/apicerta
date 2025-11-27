package br.com.nextgen.Repository;

import br.com.nextgen.Entity.SensorUmidade;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorUmidadeRepository extends JpaRepository<SensorUmidade, UUID> {
}