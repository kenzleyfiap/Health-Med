package br.com.health.validators.paciente;

import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaPacienteExistente implements ValidadorCadastroPaciente{

    private final PacienteRepository repository;
    @Override
    public void validar(PacienteDTO dados) {
        if(Boolean.TRUE.equals(repository.existsByEmail(dados.email()))) throw new ValidacaoException("Paciente já cadastrado com o e-mail informado");
        if(Boolean.TRUE.equals(repository.existsByCpf(dados.cpf()))) throw new ValidacaoException("Paciente já cadastrado com o cpf informado");
    }
}
