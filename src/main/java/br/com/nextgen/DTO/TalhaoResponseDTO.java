package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Talhao;
import br.com.nextgen.Entity.Medida;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TalhaoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        Double tamanho,
        Medida medida,
        String nomeResponsavel,
        List<String> nomesCulturas,
        List<String> descricoesOperacoes,
        Boolean ativo
) {
    public TalhaoResponseDTO(Talhao talhao) {
        this(
                talhao.getId(),
                talhao.getNome(),
                talhao.getDescricao(),
                talhao.getTamanho(),
                talhao.getMedida(),
                talhao.getUsuario().getNome(),
                talhao.getCulturas().stream().map(c -> c.getNome()).collect(Collectors.toList()),
                talhao.getOperacoes().stream().map(o -> o.getOperacao()).collect(Collectors.toList()),
                talhao.getAtivo()
        );
    }
}