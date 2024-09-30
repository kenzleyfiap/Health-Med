package br.com.health.validators.medico;


import br.com.health.dto.medico.MedicoDTO;

public interface ValidadorCadastroMedico {
    void validar(MedicoDTO dados);
}
