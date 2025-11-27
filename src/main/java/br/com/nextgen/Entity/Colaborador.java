package br.com.nextgen.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "Colaborador")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "A permissão é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "permissao", nullable = false)
    private Permissao permissao;

    @ManyToOne
    @JoinColumn(name = "id_talhao", nullable = false)
    private Talhao talhao;
//    @ManyToMany(mappedBy = "colaboradores")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Talhao> talhoes;
}
