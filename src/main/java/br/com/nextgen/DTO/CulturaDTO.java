package br.com.nextgen.DTO;

import br.com.nextgen.Entity.Cultura;
import java.time.LocalDate; // Ou String, dependendo da sua entidade. Assumindo String baseado no seu SQL anterior ou LocalDate se JPA converteu.
// Se no SQL é DATE, no Java geralmente é LocalDate. Vou usar LocalDate, se der erro troque para String.
// Olhando seu código anterior, você usou String para data em alguns lugares e LocalDate em outros.
// Vou manter o padrão mais robusto (LocalDate) ou String se for simples.
// No seu último script SQL: "data DATE NOT NULL". No Java Entity Cultura anterior não tinha data, mas no talhao_cultura tinha.
// Espere, no seu NOVO SQL a tabela Cultura NÃO tem data. A data está na tabela de relação ou sumiu?
// NOVO SQL: CREATE TABLE public.cultura (id, nome, id_usuario). NÃO TEM DATA.
// Então o DTO não deve ter data, ou deve pegar de outro lugar?
// No frontend você exibe data.
// Vou assumir que por enquanto retornamos null ou removemos a data se ela não existe na entidade.
// Vendo seu código anterior, TalhaoDetalhadoDTO usa CulturaDTO::new.
// Vou criar o DTO apenas com os campos que existem na entidade Cultura atual.

import java.util.UUID;

public record CulturaDTO(UUID id, String nome) {
    public CulturaDTO(Cultura c) {
        this(c.getId(), c.getNome());
    }
}