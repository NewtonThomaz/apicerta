package br.com.nextgen.DTO;

public class CulturaResponseDTO {
}
/*
// Crie uma classe CulturaResponseDTO.java
@Data
public class CulturaResponseDTO {
    private UUID id;
    private String nome;
    private UUID usuarioId; // Retorna só o ID, não o objeto todo
    private String usuarioNome; // Retorna o nome para exibição
    private List<Talhao> talhoes; // Ou um TalhaoDTO se quiser filtrar também

    // Construtor que aceita a Entidade Cultura
    public CulturaResponseDTO(Cultura cultura) {
        this.id = cultura.getId();
        this.nome = cultura.getNome();
        if (cultura.getUsuario() != null) {
            this.usuarioId = cultura.getUsuario().getId();
            this.usuarioNome = cultura.getUsuario().getNome();
        }
        this.talhoes = cultura.getTalhoes();
    }
}
*/