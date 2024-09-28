package br.com.health.service;

import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;

import java.time.LocalDateTime;

public interface ConsultaService {

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    ConsultaResponseDTO save(ConsultaDTO consultaDTO);
}
