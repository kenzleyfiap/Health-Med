package br.com.health.dto.paciente;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoPacienteDTO(
        @NotNull
        Long id,
        String nome,
        String email) {
}
