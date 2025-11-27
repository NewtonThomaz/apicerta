package br.com.nextgen.Repository;

import br.com.nextgen.Entity.SensorTemperatura;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, UUID> {
}