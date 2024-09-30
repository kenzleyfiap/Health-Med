package br.com.health.utils;

import br.com.health.domain.paciente.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;

public abstract class PacienteHelper {

    public static Paciente gerarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setAtivo(Boolean.TRUE);
        paciente.setId(1L);
        paciente.setEmail("paciente@mail.com");
        paciente.setNome("paciente");
        paciente.setCpf("39393978654");

        return paciente;
    }

    public static PacienteDTO gerarPacienteDTO() {
        return new PacienteDTO("paciente", "paciente@mail.com","39393978654", "teste");
    }

    public static PacienteResponseDTO gerarPacienteResponseDTO() {
        return new PacienteResponseDTO(1l, "paciente", "paciente@mail.com", "39393978654");
    }
}
