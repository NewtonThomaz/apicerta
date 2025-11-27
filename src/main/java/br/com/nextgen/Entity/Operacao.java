package br.com.nextgen.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Operacao")
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "A descrição da operação é obrigatória")
    @Size(min = 3, max = 150, message = "A descrição da operação deve ter entre 3 e 150 caracteres")
    @Column(nullable = false)
    private String operacao;

    @NotBlank(message = "O agente é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do agente deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String agente;

    @NotNull(message = "A data e hora são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_talhao", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Talhao talhao;
}
