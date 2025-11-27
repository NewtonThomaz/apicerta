package br.com.nextgen.DTO;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) de requisição para associar um Colaborador a um Talhão.
 * Utiliza 'record' (Java 16+) para garantir imutabilidade.
 */
public record ColaboradorRequestDTO(
    
    // O email do Usuário que será o novo colaborador
    @NotBlank(message = "O email não pode estar vazio.")
    @Email(message = "O formato do email é inválido.")
    String email, 
    
    // O ID do Talhão ao qual o usuário será associado
    @NotNull(message = "O ID do Talhão é obrigatório.")
    UUID idTalhao,
    
    // O nível de permissão (ex: "VISUALIZAR", "EDITAR")
    @NotBlank(message = "A permissão é obrigatória.")
    String permissao 
    
) {
    // O 'record' automaticamente gera:
    // - Construtor canônico (todos os campos)
    // - Métodos de acesso (getters) (ex: dto.email())
    // - Métodos equals(), hashCode() e toString()
}