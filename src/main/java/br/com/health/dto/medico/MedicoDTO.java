package br.com.health.dto.medico;

import br.com.health.domain.Especialidade;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record MedicoDTO(
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato do email é inválido")
        String email,
        @NotBlank(message = "CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido")
        String crm,
        @NotBlank(message = "Password é obrigatório")
        String password,
        @NotNull(message = "Especialidade é obrigatória")
        Especialidade especialidade,
        @NotEmpty(message = "Horários disponíveis é obrigatório")
        List<HorarioDisponivelDTO> horariosDisponiveis) {
}
