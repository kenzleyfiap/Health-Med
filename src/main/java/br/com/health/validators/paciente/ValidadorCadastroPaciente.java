package br.com.health.validators.paciente;


import br.com.health.dto.paciente.PacienteDTO;

public interface ValidadorCadastroPaciente {
    void validar(PacienteDTO dados);
}
