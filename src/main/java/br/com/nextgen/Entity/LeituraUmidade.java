package br.com.nextgen.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Leitura_Umidade")
public class LeituraUmidade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O sensor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_sensor", nullable = false)
    private SensorUmidade sensor;

    @NotNull(message = "A umidade é obrigatória")
    @Column(nullable = false)
    private Double umidade;

    @NotNull(message = "A data e hora são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_talhao")
    @ToString.Exclude          // <--- RECOMENDADO
    @EqualsAndHashCode.Exclude // <--- RECOMENDADO
    private Talhao talhao;
}
