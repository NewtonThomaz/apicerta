package br.com.nextgen.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cultura")
public class Cultura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome da cultura é obrigatório")
    @Size(min = 2, max = 100, message = "O nome da cultura deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToMany(mappedBy = "culturas")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Talhao> talhoes;
}
