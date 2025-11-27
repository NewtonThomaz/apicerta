package br.com.nextgen.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sensor_Temperatura")
public class SensorTemperatura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O IP do sensor de temperatura é obrigatório")
    @Size(min = 7, max = 45, message = "O IP deve ter entre 7 e 45 caracteres")
    @Column(name = "ip_temperatura", nullable = false)
    private String ip;
}
