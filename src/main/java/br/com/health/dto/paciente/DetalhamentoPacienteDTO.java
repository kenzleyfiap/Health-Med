package br.com.health.dto.paciente;

import br.com.health.domain.Paciente;

public record DetalhamentoPacienteDTO(Long id, String nome, String email, String cpf) {

    public DetalhamentoPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
