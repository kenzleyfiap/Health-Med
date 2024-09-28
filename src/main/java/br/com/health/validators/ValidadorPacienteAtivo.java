package br.com.health.validators;

import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(ConsultaDTO dados) {
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

        if(!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
