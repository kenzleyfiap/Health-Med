package br.com.health.validators.medico;

import br.com.health.dto.medico.MedicoDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaMedicoExistente implements ValidadorCadastroMedico{

    private final MedicoRepository repository;
    @Override
    public void validar(MedicoDTO dados) {
        if(Boolean.TRUE.equals(repository.existsByCpf(dados.cpf()))) throw new ValidacaoException("Médico já cadastrado com o CPF informado");
        if(Boolean.TRUE.equals(repository.existsByCrm(dados.crm()))) throw new ValidacaoException("Médico já cadastrado com o CRM informado");
        if(Boolean.TRUE.equals(repository.existsByEmail(dados.email()))) throw new ValidacaoException("Médico já cadastrado com o E-MAIL informado");
    }
}
