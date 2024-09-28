package br.com.health.validators;

import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {
    private final MedicoRepository medicoRepository;
    public void validar(ConsultaDTO dados) {
        if(dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if(Boolean.FALSE.equals(medicoEstaAtivo)) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluido");
        }
    }
}
