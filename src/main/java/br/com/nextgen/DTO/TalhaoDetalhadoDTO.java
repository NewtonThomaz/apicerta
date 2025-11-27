package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Medida;
import br.com.nextgen.Entity.Talhao;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TalhaoDetalhadoDTO(
        UUID id,
        String nome,
        String descricao,
        Double tamanho,
        Medida medida,
        Boolean ativo,
        List<CulturaDTO> culturas,
        List<OperacaoDTO> operacoes,
        List<ColaboradorDTO> colaboradores,
        SensorDTO sensorTemperatura,
        SensorDTO sensorUmidade
) {
    public static TalhaoDetalhadoDTO fromEntity(Talhao talhao) {
        return new TalhaoDetalhadoDTO(
                talhao.getId(),
                talhao.getNome(),
                talhao.getDescricao(),
                talhao.getTamanho(),
                talhao.getMedida(),
                talhao.getAtivo(),

                // Converte a lista de entidades Cultura para CulturaDTO
                talhao.getCulturas() != null
                        ? talhao.getCulturas().stream().map(CulturaDTO::new).collect(Collectors.toList())
                        : List.of(),

                // Converte a lista de entidades Operacao para OperacaoDTO
                talhao.getOperacoes() != null
                        ? talhao.getOperacoes().stream().map(OperacaoDTO::new).collect(Collectors.toList())
                        : List.of(),

                // Converte a lista de entidades Colaborador para ColaboradorDTO
                talhao.getColaboradores() != null
                        ? talhao.getColaboradores().stream().map(ColaboradorDTO::new).collect(Collectors.toList())
                        : List.of(),

                // Sensores (Envia null se não houver lógica de busca direta ainda)
                null,
                null
        );
    }
}