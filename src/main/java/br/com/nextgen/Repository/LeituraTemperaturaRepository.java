package br.com.nextgen.Repository;

import br.com.nextgen.Entity.LeituraTemperatura;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeituraTemperaturaRepository extends JpaRepository<LeituraTemperatura, UUID> {
}