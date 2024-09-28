package br.com.health.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record PacienteDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank
        String password) {
}
