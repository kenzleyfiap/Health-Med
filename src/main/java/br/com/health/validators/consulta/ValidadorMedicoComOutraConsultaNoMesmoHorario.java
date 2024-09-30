package br.com.health.validators.consulta;

import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository consultaRepository;

    public void validar(ConsultaDTO dados) {
        // Ajusta o intervalo de tempo em que a consulta pode ocorrer
        LocalDateTime inicioConsulta = dados.data().minusMinutes(30);
        LocalDateTime fimConsulta = dados.data().plusMinutes(30);

        // Verifica se o médico já tem uma consulta nesse intervalo
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }

        // Adicionalmente, pode verificar se o paciente já tem outra consulta nesse intervalo
        var pacienteTemOutraConsulta = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), inicioConsulta, fimConsulta);
        if (pacienteTemOutraConsulta) {
            throw new ValidacaoException("Paciente já possui outra consulta agendada nesse período");
        }
    }
}
