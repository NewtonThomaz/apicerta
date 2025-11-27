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
import org.hibernate.annotations.SQLDelete;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Talhao")
@SQLDelete(sql = "UPDATE Talhao SET ativo = false WHERE id = ?")
public class Talhao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do talhão é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do talhão deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
    private String descricao;

    @NotNull(message = "O tamanho é obrigatório")
    @Column(nullable = false)
    private Double tamanho;

    @NotNull(message = "O usuário responsável é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "A medida é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "medida", nullable = false)
    private Medida medida;

    @OneToMany(mappedBy = "talhao")
    @ToString.Exclude            // <--- AQUI
    @EqualsAndHashCode.Exclude   // <--- AQUI
    private List<LeituraTemperatura> leiturasTemperatura;

    @OneToMany(mappedBy = "talhao")
    @ToString.Exclude            // <--- AQUI
    @EqualsAndHashCode.Exclude   // <--- AQUI
    private List<LeituraUmidade> leiturasUmidade;

    @OneToMany(mappedBy = "talhao")
    @ToString.Exclude            // <--- AQUI
    @EqualsAndHashCode.Exclude   // <--- AQUI
    private List<Colaborador> colaboradores;

    @ManyToMany
    @JoinTable(
            name = "Talhao_Cultura",
            joinColumns = @JoinColumn(name = "id_talhao"),
            inverseJoinColumns = @JoinColumn(name = "id_cultura")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Cultura> culturas;

    @OneToMany(mappedBy = "talhao", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Operacao> operacoes;

    @Column(nullable = false)
    private Boolean ativo = true;
}
