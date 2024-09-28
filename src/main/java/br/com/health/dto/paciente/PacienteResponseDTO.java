package br.com.health.dto.paciente;

public record PacienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf) {
}
